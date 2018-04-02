package servlet.admin;

import Imp.AdminServiceImp;
import dto.MyAdmin;
import oracle.jrockit.jfr.StringConstantPool;
import service.AdminService;
import util.GetStringBuffer;
import util.JsonUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

;

public class adminServlet extends HttpServlet {
    private static  AdminService adminService = new AdminServiceImp();
    @Override
    //管理员登陆
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = GetStringBuffer.getString(req);
        Map<String,String> map = JsonUtil.stringToCollect(json);

        String username = map.get("username");
        String password = map.get("password");
        System.out.println(username+password);
        HttpSession session = req.getSession();
        JsonUtil jsonUtil = new JsonUtil();
        String str = null;
        //查询session中的admin
        Object user = session.getAttribute("IAdmin");
        if(user != null){
            str = jsonUtil.toJSONString(user);
            System.out.println("cunzai");
        }else{
            //重新登录
            MyAdmin admin = adminService.adminLogin(username,password);
            if(admin != null){
                session.setAttribute("admin",admin.getUsername());
                session.setAttribute("role", admin.getRole());
                str = jsonUtil.toJSONString(admin);
            }
            System.out.println(str);
        }
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.print(str);
        out.flush();
        out.close();

    }
}