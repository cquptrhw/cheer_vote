package servlet.startPage;

import Imp.PageInfoServiceImp;
import Imp.WeiXinServiceImp;
import service.PageInfoService;
import service.WeiXinService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 16:48 2018/4/11
 */
public class startPageServlet extends HttpServlet {
    private static PageInfoService  pageInfoService = new PageInfoServiceImp();
    private static WeiXinService weiXinService= new WeiXinServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str =null;
        HttpSession session =  req.getSession();
        String openId = "aa";
//        String openId = weiXinService.getOpenId(session);
//
//        if(openId == null || openId.equals("")){
//            str = "未获取信息";
//            resp.setContentType("text/html;charset=utf-8");
//            resp.getWriter().println(str);
//            return;
//        }
        str = pageInfoService.getStartPage(openId);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;

    }
}
