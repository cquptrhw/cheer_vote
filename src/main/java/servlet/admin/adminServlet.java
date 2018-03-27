package servlet.admin;

import Imp.AdminServiceImp;
import dto.MyAdmin;
import service.AdminService;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

;

public class adminServlet extends HttpServlet {
    private static  AdminService adminService = new AdminServiceImp();
    @Override
    //管理员登陆
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username+password);
        HttpSession session = req.getSession();
        JsonUtil jsonUtil = new JsonUtil();
        String str = null;
        //查询session中的admin
        Object user = session.getAttribute("IAdmin");
        if(user != null){
            str = jsonUtil.toJSONString(user);
        }else{
            //重新登录
            MyAdmin admin = adminService.adminLogin(username,password);
            if(admin != null){
                session.setAttribute("admin",admin.getUsername());
                session.setAttribute("role", admin.getRole());
                str = jsonUtil.toJSONString(admin);
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
    }
}