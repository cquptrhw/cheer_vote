package servlet.assistance;

import Imp.AssistanceServiceImp;
import service.AssistanceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 20:08 2018/3/30
 */
public class UserGetGroupRankServlet extends HttpServlet {
    private static AssistanceService assistanceService = new AssistanceServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = assistanceService.getGroupRank();
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);

    }
}
