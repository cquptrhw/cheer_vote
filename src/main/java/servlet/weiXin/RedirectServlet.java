package servlet.weiXin;

import Imp.WeiXinServiceImp;
import service.WeiXinService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:01 2018/4/11
 */
public class RedirectServlet extends HttpServlet {
    static WeiXinService weiXinService = new WeiXinServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String openId = weiXinService.getOpenId(session);
        System.out.println(openId);
        System.out.println(session.getAttribute("User"));
    }
}
