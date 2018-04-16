package Imp;

import dao.IAssistance;
import dto.Assistance_history;
import dto.Cheer_assistance;
import dto.Group_rank;
import dto.user_assistance;
import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import service.AssistanceService;
import servlet.answer.UserGetAnswerServlet;
import util.Const;
import util.JedisUtil;
import util.JsonUtil;
import util.SqlSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 14:35 2018/3/25
 */
public class AssistanceServiceImp implements AssistanceService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    protected static Logger logger = LoggerFactory.getLogger(UserGetAnswerServlet.class);
    //修改助力数
    @Override
    public Boolean addUserAssistance(String openId, int assistance) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        //更改mysql数据
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        int i = iAssistance.addUserAssistance(openId,assistance);
        //更改redis数据
        JedisUtil.getJedis().hset(Const.Assistance,openId, String.valueOf(assistance));
        session.close();
        if(i!=1){
            return false;
        }else {
            return true;
        }

    }

    //获取用户的助力数
    @Override
    public int getUserAssistance(String openId) {
        String num = JedisUtil.getJedis().hget(Const.Assistance,openId);
        int assistance = 0;
        //判断redis中是否有助力数
        if(num == null||num.isEmpty()){
            SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
            //从mysql获取用户的助力数,若为零就执行插入
            IAssistance iAssistance = session.getMapper(IAssistance.class);
                //判断Mysql中是否存在
                Integer k = iAssistance.getUserAssistance(openId);
                if(k==null){
                    //不存在就在mysql中插入空数据
                    int i = iAssistance.insertUserAssistance(openId);
                    if(i!=1) {
                        logger.error("错误原因 :插入失败");
                    }else {
                        assistance=0;
                    }
                }else{
                    assistance = k;
                }
                session.close();
        }
        else{
            assistance = Integer.parseInt(num);
        }
        //插入redis进行缓存
        JedisUtil.getJedis().hset(Const.Assistance,openId, String.valueOf(assistance));

        return assistance;
    }

    //获取用户助力历史
    @Override
    public String getAssistanceHistory(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        List<Assistance_history> assistance_historyList=iAssistance.getAssistanceHistory(openId);
        String str = JsonUtil.toJSONString(assistance_historyList);
//        System.out.println(str);
        session.close();
        return str;
    }

    //增加啦啦队的助力数
    @Override
    public Boolean updateCheerAssistance(List<user_assistance> user_assistanceList){
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        int i = iAssistance.updateCheerAssistance(user_assistanceList);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }

    //获取啦啦队的助力数
    @Override
    public String getCheerDistance(List<Integer> list) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        List<Cheer_assistance> cheer_assistanceList =  iAssistance.getCheerDistance(list);
        String str = JsonUtil.toJSONString(cheer_assistanceList);
        return str;
    }
    //获取战队排行
    @Override
    public String getGroupRank(){
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        List<Group_rank> group_rankList= iAssistance.getGroupRank();
        for (Group_rank group_rank:group_rankList){
            String groupId = group_rank.getGroupId();
            String group = getCheerNameByGroupId(groupId);
            group_rank.setClassName(group);
        }
        String str = JsonUtil.toJSONString(group_rankList);
        return str;
    }
    //获取战队成员
    @Override
    public String getCheerNameByGroupId(String groupId) {
        //从redis中获取组队信息
        String group = JedisUtil.getJedis().hget(Const.Group,groupId);

        if(group==null||group.isEmpty()){   //判断redis中是否存在
            SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
            IAssistance iAssistance = session.getMapper(IAssistance.class);
            List list= iAssistance.getCheerNameByGroupId(groupId);
            group = JsonUtil.toJSONString(list);
            JedisUtil.getJedis().hset(Const.Group,groupId,group);
        }

        return group;
    }

//    //判断用户是否有足够的助力数
//    @Override
//    public boolean isEnough(String openId, int acount) {
//        int assistance = getUserAssistance(openId);
//        if(assistance>=acount){
//            return true;
//        }
//        return false;
//    }


    public static void main(String[] args){
       AssistanceService assistanceService = new AssistanceServiceImp();
//        List<Integer> list = new ArrayList<Integer>();
//        list.add(7);
//        list.add(3);
//        list.add(1);
//        System.out.println(JsonUtil.toJSONString(list));
        String str = assistanceService.getGroupRank();
        System.out.println(str);
    }


}
