package servlet.assistance;

import controller.AssistanceController;
import org.json.JSONException;
import org.json.JSONObject;
import util.isNumeric;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 13:40 2018/3/13
 */
public class UpdateCheerAssistanceServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String openId = req.getParameter("openId");
        AssistanceController assistanceController = new AssistanceController();
        String str = null;
        req.setCharacterEncoding("utf-8");

        Map map = new HashMap(); //拉拉队和对应的助力数
        isNumeric isNum = new isNumeric();//验证是否有非法输入
        int acount = 0; //请求中所有助力数的和
        //遍历前段的字段和对应的值
        Enumeration rnames = req.getParameterNames();
        for (Enumeration e = rnames; e.hasMoreElements(); ) {
            String classId = e.nextElement().toString();
            String assistance = req.getParameter(classId);
            if(classId.equals("openId"))
                continue;

            if (isNum.isNumeric(assistance)) {
                acount = acount + Integer.parseInt(assistance);
//                    System.out.println(acount);
                map.put(classId, assistance);
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("status", 400);
                    jsonObject.put("message", "非法输入");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                str = jsonObject.toString();
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println(str);
                return;
            }
        }
        //判断是否超过自己具有的剩余的助力数目
        boolean result = false;
        try {
            result = assistanceController.isOverAssistance(openId,acount);
            if(result){
                str =assistanceController.updateCheerAssistance(map,openId,acount);
            }else{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", 400);
                jsonObject.put("message", "非法输入");
                str = jsonObject.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
