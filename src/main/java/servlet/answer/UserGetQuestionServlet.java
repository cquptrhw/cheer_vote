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

public class UserGetQuestionServlet extends HttpServlet{
    private static AnswerQuestionService  answerQuestionService = new AnswerQuestionServiceImp();
    private static WeiXinService weiXinService = new WeiXinServiceImp();
    //获取题目
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //根据session中openId获取openId
        HttpSession session = req.getSession();
        String openId = weiXinService.getOpenId(session);
        if(openId == null || openId.equals("")){
            String str = "未获取信息";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        String str = null;
        int todayNum = answerQuestionService.getTodayNum(openId);
        if(todayNum>=50||todayNum<0){
            str="超过今日答题数目";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        str =answerQuestionService.getQuestionFromRedis(openId,todayNum);
        if(str == null||str.isEmpty()){
            str ="获取失败";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
