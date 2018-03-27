package servlet.answer;

import Imp.AnswerQuestionServiceImp;
import service.AnswerQuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 21:30 2018/3/27
 */
public class GetTodayNumServlet extends HttpServlet {
    private static AnswerQuestionService answerQuestionService = new AnswerQuestionServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String openId = "s";
        int str = answerQuestionService.getTodayNum(openId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
