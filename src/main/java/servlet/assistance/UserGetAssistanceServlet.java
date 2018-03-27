package servlet.assistance;

import Imp.AssistanceServiceImp;
import controller.AssistanceController;
import org.json.JSONException;
import service.AssistanceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserGetAssistanceServlet extends HttpServlet {
    private static AssistanceService assistanceService = new AssistanceServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String openId = "k";
        int str = assistanceService.getUserAssistance(openId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
