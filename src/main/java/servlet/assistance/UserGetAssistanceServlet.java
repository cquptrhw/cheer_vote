package servlet.assistance;

import Imp.AssistanceServiceImp;
import Imp.WeiXinServiceImp;

import service.AssistanceService;
import service.WeiXinService;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserGetAssistanceServlet extends HttpServlet {
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

        int num = assistanceService.getUserAssistance(openId);
        Map<String,Integer> assistance = new HashMap<>();
        assistance.put("assistance",num);
        String str = JsonUtil.toJSONString(assistance);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
