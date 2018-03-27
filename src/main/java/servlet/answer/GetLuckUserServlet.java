package servlet.answer;

import Imp.getLuckUserServiceImp;
import service.GetLuckUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetLuckUserServlet extends HttpServlet {
    private static GetLuckUserService getLuckUserService = new getLuckUserServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = getLuckUserService.getLuckUserList();
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
