package servlet.assistance;

import controller.AssistanceController;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 13:44 2018/3/13
 */
public class UserGetAssistanceHistoryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String openId = req.getParameter("openId");
        AssistanceController assistanceController =new AssistanceController();
        String str = null;
        try {
            str = assistanceController.getAssistanceHistoryByOpenId(openId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
