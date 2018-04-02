package servlet.cheerInfo;

import Imp.AdminServiceImp;
import dto.Cheer_result;
import service.AdminService;
import util.JsonUtil;
import util.UploadUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class CheerInfoServlet extends HttpServlet {

    private static AdminService adminService = new AdminServiceImp();
    //啦啦队信息上传
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str = null;
        UploadUtil uploadUtil = new UploadUtil();
        HttpSession session =  request.getSession();
        boolean res = adminService.isLogin(session);
        if(!res){
            str = "请先登录";
        }else {
            HashMap<String, String> map = uploadUtil.upload(request);
            int i = adminService.updateCheerInfo(map);
            if (i != 0) {
                str = "上传成功";
            } else {
                str = "上传失败";
            }
        }
//        System.out.println(i);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(str);
    }

    //获取啦啦队是否上传信息
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str =null;
        HttpSession session =  req.getSession();
        boolean res = adminService.isLogin(session);
        if(!res){
            str = "请先登录";
        }else{
            List<Cheer_result> cheer_results = adminService.getCheerResult();
            if(cheer_results.isEmpty()){
                str="查询错误";
            }else {
                JsonUtil jsonUtil = new JsonUtil();
                str = jsonUtil.listToJsonArray(cheer_results);
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
    }

}






