package servlet.pageInfo;

import Imp.PageInfoServiceImp;
import service.PageInfoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:30 2018/4/12
 */
public class CheerPlayerInfoServlet extends HttpServlet {
    private static PageInfoService  pageInfoService = new PageInfoServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classId = req.getParameter("classId");
        String str = pageInfoService.getCheerPlayerInfo(classId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
