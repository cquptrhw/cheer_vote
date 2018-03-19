//package servlet.answer;
//
//import controller.AnswerController;
//import org.json.JSONException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class UserEndQuestionServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String openId = req.getParameter("openId");
//        AnswerController answerController =new AnswerController();
//        String str = null;
//        try {
//            str = answerController.endQuestion(openId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        resp.setContentType("text/html;charset=utf-8");
//        resp.getWriter().println(str);
//        return;
//    }
//}
