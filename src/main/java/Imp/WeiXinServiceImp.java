package Imp;

import dao.IPageInfo;
import dao.IUser;
import org.apache.ibatis.session.SqlSession;
import service.WeiXinService;
import util.SqlSessionFactoryUtil;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 21:00 2018/4/10
 */
public class WeiXinServiceImp implements WeiXinService{
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //插入用户信息
    @Override
    public boolean insertUserInfo(Map<String, String> userInfo) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IUser iUser = session.getMapper(IUser.class);
        int i = iUser.insertUserInfo(userInfo);
        if(i!=0){
            return true;
        }else {
            return false;
        }
    }
    //获取用户信息
    @Override
    public Map<String,String> getUserInfo(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IUser iUser = session.getMapper(IUser.class);
        Map<String,String> user= iUser.getUserInfo(openId);
        session.close();
        return user;
    }
    //从session中获取openId
    @Override
    public String getOpenId(HttpSession session) {
        Map<String,String> user = (Map<String, String>) session.getAttribute("User");
        String openId = null;
        if(!(user == null || user.isEmpty())) {
            openId = user.get("openId");
        }
        return openId;
    }


}
