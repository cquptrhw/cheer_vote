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
 * @Date: Created in 18:12 2018/3/13
 */
public class QuestionServlet extends HttpServlet {
    private static AdminService adminService = new AdminServiceImp();
    //上传题目
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String json = GetStringBuffer.getString(req);
        Map<String,String> map = JsonUtil.stringToCollect(json);
        String str = null;

        HttpSession session =  req.getSession();
        int  res =adminService.isMainAdmin(session);
        if(res == 1){
            str = "请先登录";
        }else if (res == 2){
            str = "请使用主管理员账号进行此操作";
        } else {
//            Question ques = new Question();
//            ques.setTitle(map.get("question"));
//            ques.setA(map.get("A"));
//            ques.setB(map.get("B"));
//            ques.setC(map.get("C"));
//            ques.setD(map.get("D"));
//            ques.setAnswer(map.get("answer"));
//            ques.setKind(req.getParameter("kind"));
            int i = adminService.updateQuestion(map);
            if(i != 0){
                str = "上传成功";
            }else{
                str = "上传失败";
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
    //删除题目
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = null;
        AdminService adminService = new AdminServiceImp();
        req.setCharacterEncoding("utf-8");
        String questionId = req.getParameter("questionId");
        HttpSession session =  req.getSession();
        if(questionId==null||questionId.isEmpty()){
            str = "错误";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        int  res =adminService.isMainAdmin(session);
        if(res == 1){
            str = "请先登录";
        }else if (res == 2){
            str = "请使用主管理员账号进行此操作";
        } else {
            int  res2 = adminService.delQuestionById(Integer.parseInt(questionId));
            if(res!=0){
                str= "删除成功";
            }else{
                str="删除失败";
            }

        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
    }
    //根据关键字查题目
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = null;
        HttpSession session =  req.getSession();
        String key = new String(req.getParameter("key").getBytes("ISO-8859-1"),"UTF-8");
        if(key==null||key.isEmpty()){
            str = "参数错误";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }
        int  res =adminService.isMainAdmin(session);
        if(res == 1){
            str = "请先登录";
        }else if (res == 2){
            str = "请使用主管理员账号进行此操作";
        } else {
            List<Question> question_nums = adminService.getQuestionByKey(key);
            JsonUtil jsonUtil = new JsonUtil();
            str = jsonUtil.listToJsonArray(question_nums);
            System.out.println(str);
        }

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);

    }


}
