package servlet.message;

import Imp.MessageServiceImp;
import controller.MessageController;
import org.json.JSONException;
import service.MessageService;
import util.GetStringBuffer;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 18:07 2018/3/13
 */
public class PraiseMessageServlet extends HttpServlet {
    private  static MessageService messageService = new MessageServiceImp();
    //用户点赞
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = null;
        String data = GetStringBuffer.getString(req);
        Map<String,String> jsonMap = JsonUtil.stringToCollect(data);
        String contentId = jsonMap.get("contentId");
        //判断是否登录，并获得openId
//        HttpSession session = req.getSession();
//        String sessionId = session.getId();
//        String openId = messageService.isLogin(sessionId);
//        if(openId == null || openId.isEmpty()){
//            str = "请重新跳转";
//        }else {
        String openId = "158";
        str = messageService.praiseMessage(openId,contentId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
//        }


    }

}
