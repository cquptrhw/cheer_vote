package servlet.question;

import Imp.AdminServiceImp;
import dto.Question;
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
 * @Date: Created in 18:16 2018/3/13
 */
public class GetQuestionListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = null;
        HttpSession session =  req.getSession();
        String kind = req.getParameter("kind");
        if(kind==null||kind.isEmpty()){
            str = "参数错误";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        Object user = session.getAttribute("admin");
        if(user == null ){
            str = "请先登录";
        }else {
            AdminService adminService = new AdminServiceImp();
            List<Question> question_nums = adminService.getQuestionList(Integer.parseInt(kind));
            JsonUtil jsonUtil = new JsonUtil();
            str = jsonUtil.listToJsonArray(question_nums);
            System.out.println(str);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);

    }
}
