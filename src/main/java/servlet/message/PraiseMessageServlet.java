package servlet.message;

import controller.MessageController;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 18:07 2018/3/13
 */
public class PraiseMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String str = null;
        String openId = req.getParameter("openId");
        String contentId = req.getParameter("contentId");
        MessageController messageController = new MessageController();
        try {
            str = messageController.praiseMessage(openId,contentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }

}
