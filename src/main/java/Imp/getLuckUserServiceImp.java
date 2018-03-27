package Imp;

import dao.LuckUser;
import org.apache.ibatis.session.SqlSession;
import service.GetLuckUserService;
import util.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:52 2018/3/26
 */
public class getLuckUserServiceImp implements GetLuckUserService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //从mysql获取幸运用户
    @Override
    public String getLuckUser() {
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        LuckUser luckUser = session.getMapper(LuckUser.class);
        List<LuckUser> luckUserList = luckUser.getLuckUser(timeArray[0],timeArray[1]);
//        System.out.println(timeArray[0]+"..."+timeArray[1]);
        String str = JsonUtil.toJSONString(luckUserList);
        session.close();
        return str;
    }
    //插入幸运用户
    @Override
    public int insertLuckUser(String user_list) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        LuckUser luckUser = session.getMapper(LuckUser.class);
        int i = luckUser.insertLuckUser(user_list);
        session.close();
        return  i;

    }
    //查询当日的幸运用户
    @Override
    public String getLuckUserList() {
        String str = JedisUtil.getString(Const.LuckUser);
        if(str== null||str.isEmpty()){
            //如果从redis没有查到，就去mysql获取
            SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
            LuckUser luckUser = session.getMapper(LuckUser.class);
           str =  luckUser.getLuckUserList();
           //将查询结果插入redis
            JedisUtil.setString(Const.LuckUser,str);
        }
        return str;
    }


    public static void main(String[] args){
        GetLuckUserService getLuckUserService =new getLuckUserServiceImp();
        String str = getLuckUserService.getLuckUser();
//        HashMap m1 = new HashMap();
//        m1.put("openId", "8");
//        m1.put("classId", "31");
//        m1.put("content", "你好");
        System.out.println(str);
    }
}
