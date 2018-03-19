package servlet.question;

import Imp.AdminServiceImp;
import dto.Question_num;
import service.AdminService;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 18:15 2018/3/13
 */
public class GetQuestionNumServlet extends HttpServlet {
    private static AdminService adminService = new AdminServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str =null;
        HttpSession session =  req.getSession();
        Object user = session.getAttribute("admin");
        if(user == null){
            str = "请先登录";
        }else{
            List<Question_num> question_nums=adminService.getQuestionNum();
            if(question_nums.isEmpty()){
                str="查询错误";
            }else {
                JsonUtil jsonUtil = new JsonUtil();
                str = jsonUtil.listToJsonArray(question_nums);
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
    }
}
