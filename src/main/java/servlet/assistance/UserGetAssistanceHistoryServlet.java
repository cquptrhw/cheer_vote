package servlet.assistance;

import Imp.AssistanceServiceImp;
import Imp.WeiXinServiceImp;
import service.AssistanceService;
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
 * @Date: Created in 13:44 2018/3/13
 */
public class UserGetAssistanceHistoryServlet extends HttpServlet {
    private static AssistanceService assistanceService = new AssistanceServiceImp();
    private static WeiXinService weiXinService = new WeiXinServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String openId = weiXinService.getOpenId(session);
        if(openId == null || openId.equals("")){
            String str = "未获取信息";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        String str = assistanceService.getAssistanceHistory(openId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
