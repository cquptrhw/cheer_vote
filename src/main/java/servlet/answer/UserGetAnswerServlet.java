package servlet.answer;

import Imp.AnswerQuestionServiceImp;
import Imp.AssistanceServiceImp;
import Imp.WeiXinServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AnswerQuestionService;
import service.AssistanceService;
import service.WeiXinService;
import util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class UserGetAnswerServlet extends HttpServlet{
    private static AnswerQuestionService answerQuestionService = new AnswerQuestionServiceImp();
    private  static AssistanceService assistanceService = new AssistanceServiceImp();
    protected static Logger logger = LoggerFactory.getLogger(UserGetAnswerServlet.class);
    private static WeiXinService weiXinService = new WeiXinServiceImp();
    //获取答案
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String data = GetStringBuffer.getString(req);
        System.out.println(data);
        Map<String,String> jsonMap = JsonUtil.stringToCollect(data);
        System.out.println("jsonMap"+jsonMap);
        //获取openId
        HttpSession session = req.getSession();
        String openId = weiXinService.getOpenId(session);
        if(openId == null || openId.equals("")){
            String str = "未获取信息";
            System.out.println("no info");
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }

//        //判断是否在答题
//        if(JedisUtil.getString("IsAnswer"+openId)=="1"){
//            String str = "别投得太快，稍微休息一下";
//            resp.setContentType("text/html;charset=utf-8");
//            resp.getWriter().println(str);
//            return;
//        }
//        JedisUtil.setString("IsAnswer"+openId,"1");
        //获取参数
        String str = null;
        String string = jsonMap.get("string");
        String timestamp = jsonMap.get("timestamp");
        System.out.println(timestamp);
        String nonce = jsonMap.get("nonce");
        String signature = jsonMap.get("signature");
        String questionId ;
        String userAnswer ;
        String rightAnswer ;
        try {
           boolean res1 = DataUtil.getData(timestamp,nonce,string,signature);
           if(res1){
               //获取数据
               String json = EncryptUtil.decryptBASE64(string);
               Map map= JsonUtil.stringToCollect(json);
               map.put("openId",openId);
               //判断用户是否已经答过此题
               boolean re =answerQuestionService.isAnswer(map);
               if(re){
                   //答过此题
                   str = "你今天答过此题了哦";
                   resp.setContentType("text/html;charset=utf-8");
                   resp.getWriter().println(str);
               }else {    //没有答过
                   //获取正确答案
                   questionId = String.valueOf(map.get("questionId"));
                   userAnswer = String.valueOf(map.get("userAnswer"));
                   rightAnswer =answerQuestionService.getAnswerFromRedis(questionId);
                   //插入正确答案
                   map.put("rightAnswer",rightAnswer);
                   System.out.println("成功");

                   if(userAnswer.equals(rightAnswer)){
                       //回答正确
                       map.put("status",1);
                       str = JsonUtil.toJSONString(map);
                       //获取用户的助力数
                       int assistance = assistanceService.getUserAssistance(openId);
                       int num =assistance+1;
                       boolean res = assistanceService.addUserAssistance(openId,num);
                       if(!res){
                           logger.error("错误信息 ：更新分数错误");
                       }
                   }else{
                       System.out.println("shibai");
                       map.put("status",0);
                       str = JsonUtil.toJSONString(map);
                   }
                   resp.setContentType("text/html;charset=utf-8");
                   resp.getWriter().println(str);
                   //插入答题历史
                   answerQuestionService.addAnswerHistory(map);
               }


           }else {
               str = "请检查数据是否过期，或者数据被修改";
               resp.setContentType("text/html;charset=utf-8");
               resp.getWriter().println(str);
           }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("错误信息"+e.getMessage());
        }
//        JedisUtil.setString("IsAnswer"+openId,"0");
        return;
    }
}
