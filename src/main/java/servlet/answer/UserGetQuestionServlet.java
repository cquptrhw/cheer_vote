package servlet.answer;
import Imp.AnswerQuestionServiceImp;
import service.AnswerQuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserGetQuestionServlet extends HttpServlet{
    private static AnswerQuestionService  answerQuestionService = new AnswerQuestionServiceImp();
    //获取题目
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //根据session中openId获取openId
        String openId = "5agaigd";
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
