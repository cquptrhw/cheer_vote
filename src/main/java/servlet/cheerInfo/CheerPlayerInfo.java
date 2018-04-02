package servlet.cheerInfo;

import Imp.AdminServiceImp;
import service.AdminService;
import util.UploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:04 2018/3/15
 */
public class CheerPlayerInfo extends HttpServlet {
    private  static AdminService adminService = new AdminServiceImp();
    @Override
    //啦啦队成员信息上传
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = null;
        UploadUtil uploadUtil = new UploadUtil();
        HashMap<String,String> map= uploadUtil.upload(req);
        HttpSession session =  req.getSession();
        boolean res = adminService.isLogin(session);
        if(!res){
            str = "请先登录";
        }else {
            int i = adminService.updateCheerPlayerInfo(map);
            if (i != 0) {
                str = "上传成功";
            } else {
                str = "上传失败";
            }
            System.out.println(i);
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);

    }
}
