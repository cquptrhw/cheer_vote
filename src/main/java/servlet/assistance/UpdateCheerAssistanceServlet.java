package servlet.assistance;

import Imp.AssistanceServiceImp;
import Imp.WeiXinServiceImp;
import dto.user_assistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 13:40 2018/3/13
 */
public class UpdateCheerAssistanceServlet extends HttpServlet{
    protected static Logger logger = LoggerFactory.getLogger(UpdateCheerAssistanceServlet.class);
    private static WeiXinService weiXinService = new WeiXinServiceImp();
    private static  AssistanceService assistanceService = new AssistanceServiceImp();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String data = GetStringBuffer.getString(req);
        Map<String,String> jsonMap = JsonUtil.stringToCollect(data);
        //获取参数
        String str = null;
        String string = jsonMap.get("string");
        String timestamp = jsonMap.get("timestamp");
        String nonce = jsonMap.get("nonce");
        String signature = jsonMap.get("signature");

        //获取openId
        HttpSession session = req.getSession();

        String openId = weiXinService.getOpenId(session);
        if(openId == null || openId.equals("")){
            str = "未获取信息";
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println(str);
            return;
        }

        //判断参数
        isNumeric isNum = new isNumeric();//验证是否有非法输入
        List<Integer> groupIdList = new ArrayList<Integer>(); //拿出所有的groupId
        int acount = 0;
        try {
            boolean res = DataUtil.getData(timestamp,nonce,string,signature);
            if (res){
                String json = EncryptUtil.decryptBASE64(string);
                List<user_assistance> user_assistanceList = JsonUtil.toList(json,user_assistance.class);
                //加入openId,并判断num是否为数字
                for (user_assistance user_assistance:user_assistanceList){

                    String assistance = user_assistance.getNum();
                    if (isNum.isNumeric(assistance)){
                        acount = acount + Integer.parseInt(assistance);
                        user_assistance.setOpenId(openId);
                        groupIdList.add(user_assistance.getGroupId());
                    }else{
                        str = "非法字符";
                        resp.setContentType("text/html;charset=utf-8");
                        resp.getWriter().println(str);
                        return;
                    }
                }
                //判断用户的助力数是否足够
                int assistance = assistanceService.getUserAssistance(openId);
                if(assistance>=acount){
                    boolean k  = assistanceService.updateCheerAssistance(user_assistanceList);
                    if(k){
                        //扣除用户助力数
                        assistance = assistance -acount;
                        boolean ress =assistanceService.addUserAssistance(openId,assistance);
                        if(ress){
                            //助力成功后，重新获取
                            str = assistanceService.getCheerDistance(groupIdList);
                        }else{
                            str = "扣除助力数失败";
                            logger.error("错误信息"+openId+"..."+str);
                        }
                    }else{
                        str = "插入助力历史失败";
                        logger.error("错误信息"+openId+"..."+str);
                    }
                }else{
                    str = "助力数不足";
                    logger.error("错误信息"+openId+"..."+str);
                }

            }else {
                str = "请检查数据是否过期，或者数据被修改";
                logger.error("错误信息"+openId+"..."+str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("错误信息"+e.getMessage());
        }


        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(str);
        return;
    }
}
