package Imp;
import dao.IQuestion;
import dao.LuckUser;
import dto.Qusetion_user;
import org.apache.ibatis.session.SqlSession;
import service.AnswerQuestionService;
import util.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static Imp.AssistanceServiceImp.logger;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:27 2018/3/22
 */
public class AnswerQuestionServiceImp implements AnswerQuestionService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;

    //获取今日答题数
    @Override
    public int getTodayNum(String openId) {
        String num = JedisUtil.getJedis().hget(Const.todayNum,openId);
        int todayNum =0;
        if(num == null || num.isEmpty()){
            todayNum =getTodayNumFromMysql(openId);
        }else{
            todayNum= Integer.parseInt(num);
        }
        return todayNum;
    }
    //从mysql获取今日答题数
    @Override
    public int getTodayNumFromMysql(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IQuestion iQuestion = session.getMapper(IQuestion.class);
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        int todayNum = iQuestion.getTodayNumFromMysql(openId,timeArray[0],timeArray[1]);
        session.close();
        JedisUtil.getJedis().hset(Const.todayNum,openId,String.valueOf(todayNum));
        JedisUtil.getJedis().pexpire(Const.todayNum,Time.getTimeDiff());
        return todayNum;
    }

    //从Redis获取题目
    @Override
    public String getQuestionFromRedis(String openId, int todayNum) {
        String key = Const.questionKey+openId;
        int num = todayNum+1;
        String fileds = Const.questionField+num;
        String question = JedisUtil.getJedis().hget(key,fileds);
        if(question == null || question.isEmpty()){
            //从mysql获取题目
            getQusertionFromMysql(openId);
            question = JedisUtil.getJedis().hget(key,fileds);
        }
        Map map = JsonUtil.stringToCollect(question);
        map.put(Const.todayNum,num);
        //更新今日答题数
        JedisUtil.getJedis().hset(Const.todayNum,openId,String.valueOf(num));
        JedisUtil.getJedis().pexpire(Const.todayNum,Time.getTimeDiff());
        question = JsonUtil.toJSONString(map);
        return question;
    }
    //从mysql获取题目
    @Override
    public boolean getQusertionFromMysql(String openId) {
        String key =Const.questionKey+openId;
        String key2= Const.Answer;
        int kind =1;
        int num =0;
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IQuestion iQuestion = session.getMapper(IQuestion.class);
        for(; kind<=5; kind++) {
            List<Qusetion_user> list = iQuestion.getQuestionFromMysql(kind);
            for(Qusetion_user qusetion_user :list){
                //将答案插入redis
                String questionId = String.valueOf(qusetion_user.getQuestionId());
                String answer = qusetion_user.getAnswer();
                qusetion_user.setAnswer(null);
                JedisUtil.getJedis().hset(key2, questionId,answer );
                //将题目插入redis
                num++;
                String str = JsonUtil.toJSONString(qusetion_user);
                JedisUtil.getJedis().hset(key,Const.questionField+num,str);

                //设置过期时间
                long diff = Time.getTimeDiff();
                JedisUtil.getJedis().pexpire(key,diff);
                JedisUtil.getJedis().pexpire(key2,diff);
            }

        }
        session.close();
        return true;
    }
    //从redis获取答案
    @Override
    public String getAnswerFromRedis(String questionId) {
        String str = JedisUtil.getJedis().hget(Const.Answer,questionId);
        if(str == null ||str.isEmpty()){
            str = getAnswerFromMysql(questionId);
        }
        return str;
    }
    //从Mysql获取答案
    @Override
    public String getAnswerFromMysql(String questionId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IQuestion iQuestion = session.getMapper(IQuestion.class);
        String str = iQuestion.getAnswerFromMysql(questionId);
        session.close();
        return str;
    }
    //加入答题历史
    @Override
    public boolean addAnswerHistory(Map map) {
        //往mysql中添加答题记录
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IQuestion iQuestion = session.getMapper(IQuestion.class);
        int i = iQuestion.insertAnswerHistory(map);
        session.close();
        //往redis中插入答题记录
        String openId = String.valueOf(map.get("openId"));
        String questionId = String.valueOf(map.get("questionId"));
        long diff = Time.getTimeDiff();
        Long res = JedisUtil.getJedis().sadd(Const.IsAnswer+openId,questionId);
        JedisUtil.getJedis().pexpire(Const.IsAnswer+openId,diff);
        if(i != 1 || res != 1){
            logger.error("错误信息 :"+openId+"插入答题历史错误");
            return false;
        }else {
            return true;
        }


    }
    //判断用户是否答过此题
    @Override
    public boolean isAnswer(Map map) {
        String openId = String.valueOf(map.get("openId"));
        String questionId = String.valueOf(map.get("questionId"));
        boolean res = JedisUtil.getJedis().sismember(Const.IsAnswer+openId,questionId);
        return res;
    }
    //获取用户答题榜单
    @Override
    public String getUserRank() {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IQuestion iQuestion = session.getMapper(IQuestion.class);
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        List<LuckUser> luckUserList = iQuestion.getUserRank(timeArray[0],timeArray[1]);
        String str = JsonUtil.toJSONString(luckUserList);
        session.close();
        return str;
    }

//
//    public static void main(String[] args){
//        AnswerQuestionService messageService = new AnswerQuestionServiceImp();
////        HashMap m1 = new HashMap();
////        m1.put("openId", "8");
////        m1.put("classId", "31");
////        m1.put("content", "你好");
//       int  str = messageService.getTodayNumFromMysql("a");
//        System.out.println(str);
//    }
}
