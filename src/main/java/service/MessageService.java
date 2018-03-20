package service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 22:51 2018/3/20
 */
public interface MessageService {
    //上传留言
    public HashMap<String, String> updateMessage(HashMap map);
    //判断该用户是否已经登录
    public String isLogin(HttpSession session);
}
