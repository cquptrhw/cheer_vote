package servlet.question;

import Imp.AdminServiceImp;
import dto.Question;
import service.AdminService;
import util.GetStringBuffer;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 18:16 2018/3/13
 */
public class GetQuestionListServlet extends HttpServlet {
    private static AdminService adminService = new AdminServiceImp();
    //获取题目列表
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
    //修改题目
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = null;
        req.setCharacterEncoding("utf-8");
        String json = GetStringBuffer.getString(req);
        Map<String,String> map = JsonUtil.stringToCollect(json);
        HttpSession session =  req.getSession();
        int  res =adminService.isMainAdmin(session);
//        if(res == 1){
//            str = "请先登录";
//        }else if (res == 2){
//            str = "请使用主管理员账号进行此操作";
//        }else{
//            Question ques = new Question();
//            ques.setTitle(req.getParameter("question"));
//            ques.setA(req.getParameter("A"));
//            ques.setB(req.getParameter("B"));
//            ques.setC(req.getParameter("C"));
//            ques.setD(req.getParameter("D"));
//            ques.setAnswer(req.getParameter("answer"));
//            ques.setKind(req.getParameter("kind"));
//            ques.setQuestionId(Integer.parseInt(req.getParameter("questionId")));

            int i = adminService.updateQuestionById(map);
            if(i != 0){
                str = JsonUtil.toJSONString(map);
            }else{
                str = "上传失败";
            }
//        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
