package servlet.message;

import controller.MessageController;
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
 * @Date: Created in 18:05 2018/3/13
 */
public class MessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String str = null;
        String openId = req.getParameter("openId");
        String content = req.getParameter("content");
        String classId = req.getParameter("classId");
        MessageController messageController = new MessageController();
        try {
            str = messageController.updateMessage(openId,content,classId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageController messageController = new MessageController();
//        System.out.println(req.getParameter("page"));
//        System.out.println(req.getParameter("classId"));
        int  page = Integer.parseInt(req.getParameter("page"));
        int classId = Integer.parseInt(req.getParameter("classId"));
        String str = null;
        try {
            str = messageController.getMessage(page,classId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
