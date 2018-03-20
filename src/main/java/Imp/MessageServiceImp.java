package Imp;

import dao.IMessage;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import service.MessageService;
import util.SqlSessionFactoryUtil;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:20 2018/3/21
 */
public class MessageServiceImp implements MessageService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //判断用户是否已经登录
    @Override
    public HashMap<String, String> updateMessage(HashMap map) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IMessage iMessage=session.getMapper(IMessage.class);
            int id = iMessage.updateMessage(map);
            if (id != 0){
                return map;
            }
        } finally {
            session.close();
        }
        return map;
    }

    //判断用户是否已经登录
    @Override
    public String isLogin(HttpSession session) {
        String  user = (String) session.getAttribute("user");
        if(user == null){
            return null;
        }else {
            return user;
        }
    }

    public static void main(String[] args) throws SQLException, JSONException {
        MessageService messageService = new MessageServiceImp();
        HashMap m1 = new HashMap();
        m1.put("openId", "8");
        m1.put("classId", "31");
        m1.put("content", "你好");
        messageService.updateMessage(m1);
    }
}
