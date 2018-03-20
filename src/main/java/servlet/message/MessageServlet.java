//package servlet.message;
//
//import Imp.MessageServiceImp;
//import controller.MessageController;
//import org.json.JSONException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import service.MessageService;
//import util.DataUtil;
//import util.JsonUtil;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.websocket.Session;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Author: REN
// * @Description:
// * @Date: Created in 18:05 2018/3/13
// */
//public class MessageServlet extends HttpServlet {
//
//    protected static Logger logger = LoggerFactory.getLogger(MessageServlet.class);
//
//    private  static MessageService messageService = new MessageServiceImp();
//    //上传留言
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //获取参数
//        String str = null;
//        String string = req.getParameter("string");
//        long timestamp = Long.parseLong(req.getParameter("timestamp"));
//        String nonce = req.getParameter("nonce");
//        String signature = req.getParameter("signature");
//        //
//        HttpSession session = req.getSession();
//        String user = messageService.isLogin(session);
//        if(user == null || user.isEmpty()){
//            str = "请重新跳转";
//        }else {
//            //检验数据的有效性
//            try {
//                HashMap map = (HashMap) DataUtil.getData(timestamp,nonce,string,signature);
//                HashMap map1= messageService.updateMessage(map);
//                str = JsonUtil.toJSONString(map1);
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//                logger.error("错误信息"+e.getMessage());
//                return;
//            }
//        }
//
//
//
//
//        resp.setContentType("text/html;charset=utf-8");
//        resp.getWriter().println(str);
//        return;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        MessageController messageController = new MessageController();
////        System.out.println(req.getParameter("page"));
////        System.out.println(req.getParameter("classId"));
//        int  page = Integer.parseInt(req.getParameter("page"));
//        int classId = Integer.parseInt(req.getParameter("classId"));
//        String str = null;
//        try {
//            str = messageController.getMessage(page,classId);
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
