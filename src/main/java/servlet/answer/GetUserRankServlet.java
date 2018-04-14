package servlet.answer;

import Imp.AnswerQuestionServiceImp;
import service.AnswerQuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetUserRankServlet extends HttpServlet{
    private  static  AnswerQuestionService answerQuestionService = new AnswerQuestionServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = answerQuestionService.getUserRank();
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
