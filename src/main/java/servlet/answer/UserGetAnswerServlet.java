package servlet.answer;

import Imp.AnswerQuestionServiceImp;
import Imp.AssistanceServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AnswerQuestionService;
import service.AssistanceService;
import util.DataUtil;
import util.JsonUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class UserGetAnswerServlet extends HttpServlet{
    private static AnswerQuestionService answerQuestionService = new AnswerQuestionServiceImp();
    private  static AssistanceService assistanceService = new AssistanceServiceImp();
    protected static Logger logger = LoggerFactory.getLogger(UserGetAnswerServlet.class);
    //获取答案
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str= null;
        String string = req.getParameter("string");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String signature = req.getParameter("signature");
        String questionId ;
        String userAnswer ;
        String rightAnswer ;
        String openId = "AAAA00";
        try {
            Map map = DataUtil.getData(timestamp,nonce,string,signature);
            //获取openId
            map.put("openId",openId);
            if (map==null||map.isEmpty()){
                str = "请检查数据是否过期，或者数据被修改";
                resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println(str);
            }else {
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

                    if(userAnswer.equals(rightAnswer)){
                        //回答正确
                        map.put("status",1);
                        str = JsonUtil.toJSONString(map);
                        //获取用户的助力数
                        int assistance = assistanceService.getUserAssistance(openId);
                        boolean res = assistanceService.addUserAssistance(openId,assistance);
                        if(!res){
                            logger.error("错误信息 ：更新分数错误");
                        }

                    }else{
                        map.put("status",0);
                        str = JsonUtil.toJSONString(map);
                    }
                    resp.setContentType("text/html;charset=utf-8");
                    resp.getWriter().println(str);
                    //插入答题历史
                    answerQuestionService.addAnswerHistory(map);
                }

            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("错误信息"+e.getMessage());
        }
        return;
    }
}
