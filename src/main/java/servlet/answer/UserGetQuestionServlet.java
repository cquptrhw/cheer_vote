package servlet.answer;

import controller.AnswerController;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserGetQuestionServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String openId = req.getParameter("openId");
        int todayNum = Integer.parseInt(req.getParameter("todayNum"));
        String str = null;
        AnswerController answerController =new AnswerController();
        if(todayNum==0){
            //判断当天是否答过题
            int rs = answerController.isAnswer(openId);
            if(rs == 0){  //当天没有答过题
                try {
                    str = answerController.getNewQuestion(openId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    str = answerController.getOldQuestion(openId,rs);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else if(todayNum>50 || todayNum< 0){
            JSONObject result = new JSONObject();
            String message = "超过今日答题数";
            try {
                result.put("status",200);
                result.put("message",message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            str=result.toString();
        }else{
            try {
                str = answerController.getOldQuestion(openId,todayNum);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
