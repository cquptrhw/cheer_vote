package Imp;

import dao.LuckUser;
import org.apache.ibatis.session.SqlSession;
import service.GetLuckUserService;
import util.JsonUtil;
import util.SqlSessionFactoryUtil;
import util.Time;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:52 2018/3/26
 */
public class getLuckUserServiceImp implements GetLuckUserService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    @Override
    public String getLuckUser() {
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        LuckUser luckUser = session.getMapper(LuckUser.class);
        List<LuckUser> luckUserList = luckUser.getLuckUser(timeArray[0],timeArray[1]);
        String str = JsonUtil.toJSONString(luckUserList);
        System.out.println(timeArray[0]+"..."+timeArray[1]);
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
