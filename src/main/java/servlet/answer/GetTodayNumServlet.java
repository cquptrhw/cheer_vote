package servlet.answer;

import Imp.AnswerQuestionServiceImp;
import Imp.WeiXinServiceImp;
import service.AnswerQuestionService;
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
 * @Date: Created in 21:30 2018/3/27
 */
public class GetTodayNumServlet extends HttpServlet {
    private static AnswerQuestionService answerQuestionService = new AnswerQuestionServiceImp();
    private static WeiXinService weiXinService = new WeiXinServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String openId = weiXinService.getOpenId(session);
        if(openId == null || openId.equals("")){
            String str = "未获取到用户身份";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        int todayNum = answerQuestionService.getTodayNum(openId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(todayNum);
        return;
    }
}
