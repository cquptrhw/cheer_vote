package Imp;
import dao.IQuestion;
import dto.Qusetion_user;
import org.apache.ibatis.session.SqlSession;
import service.AnswerQuestionService;
import util.JedisUtil;
import util.JsonUtil;
import util.SqlSessionFactoryUtil;
import util.Time;

import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:27 2018/3/22
 */
public class AnswerQuestionServiceImp implements AnswerQuestionService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //判断是否当天第一次答题
    @Override
    public boolean isNewAnswer(String openId) {
        String num = JedisUtil.getJedis().hget("TodayNum",openId);
        if(num == null || num.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    //从Redis获取题目
    @Override
    public String getQuestionFromRedis(String openId, int todayNum) {
        String key ="Question :"+openId;
        int num = todayNum+1;
        String fileds = "key"+num;
        String question = JedisUtil.getJedis().hget(key,fileds);
        if(question == null || question.isEmpty()){
            //从mysql获取题目
           getQusertionFromMysql(openId);
           question = JedisUtil.getJedis().hget(key,fileds);
        }
        Map map = JsonUtil.stringToCollect(question);
        map.put("todayNum",num);
        question = JsonUtil.toJSONString(map);
        return question;
    }
    //从mysql获取题目
    @Override
    public boolean getQusertionFromMysql(String openId) {
        String key ="Question :"+openId;
        String key2 ="Answer:";
        int kind =1;
        int num =0;
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IQuestion iQuestion = session.getMapper(IQuestion.class);
        for(; kind<=5; kind++) {
            List<Qusetion_user> list = iQuestion.getQuestionFromMysql(kind);
            for(Qusetion_user qusetion_user :list){
                //将题目插入redis
                num++;
                String str = JsonUtil.toJSONString(qusetion_user);
                JedisUtil.getJedis().hset(key,"key"+num,str);
                //将答案插入redis
                String questionId = String.valueOf(qusetion_user.getQuestionId());
                String answer = qusetion_user.getAnswer();
                JedisUtil.getJedis().hset(key2, questionId,answer );
                //设置过期时间
                long diff = Time.getTimeDiff();
                JedisUtil.getJedis().pexpire(key,diff);
                JedisUtil.getJedis().pexpire(key2,diff);
            }

        }
        return true;
    }


    public static void main(String[] args){
        AnswerQuestionService messageService = new AnswerQuestionServiceImp();
//        HashMap m1 = new HashMap();
//        m1.put("openId", "8");
//        m1.put("classId", "31");
//        m1.put("content", "你好");
        String str  = messageService.getQuestionFromRedis("5555",5);
        System.out.println(str);
    }
}
