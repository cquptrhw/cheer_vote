package servlet.message;

import Imp.MessageServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MessageService;
import util.DataUtil;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 18:05 2018/3/13
 */
public class MessageServlet extends HttpServlet {

    protected static Logger logger = LoggerFactory.getLogger(MessageServlet.class);

    private  static MessageService messageService = new MessageServiceImp();
    //上传留言
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数
        String str = null;
        String string = req.getParameter("string");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String signature = req.getParameter("signature");


        //验证是否登录
//        HttpSession session = req.getSession();
//        String sessionId = session.getId();
//        String user = messageService.isLogin(sessionId);
//        if(user == null || user.isEmpty()){
//            str = "请重新跳转";
//        }else {
            //检验数据的有效性
            try {
                //获取json中的数据
                Map<String,String> map =  DataUtil.getData(timestamp,nonce,string,signature);
                map.put("openId","LLL");  //将改为从seesion获取的openId插入
//                System.out.println(JsonUtil.toJSONString(map));
                //插入数据
                if (map==null||map.isEmpty()){
                    str = "请检查数据是否过期，或者数据被修改";
                }else {
                    Map<String,String> map1= messageService.updateMessage(map);
                    str = JsonUtil.toJSONString(map1);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                logger.error("错误信息"+e.getMessage());
                return;
            }
//        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int  page = Integer.parseInt(req.getParameter("page"));
        String classId = req.getParameter("classId");
        String str = messageService.getMessageList(classId,page);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;

    }
}
