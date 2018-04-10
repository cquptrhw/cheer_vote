package servlet.weiXin;

import Imp.WeiXinServiceImp;
import service.WeiXinService;
import util.Const;
import util.CurlUtil;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:21 2018/4/10
 */
public class GetUserInfoFromWeiXinServlet extends HttpServlet {
    static WeiXinService  weiXinService = new WeiXinServiceImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户toekn
        String code = req.getParameter("code");
        String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ Const.AppId+"&secret="+Const.AppSecret+"&code="+code+"&grant_type=authorization_code";
        String codeResult = CurlUtil.getContent(codeUrl, null, "GET");
        String openId = String.valueOf(JsonUtil.stringToCollect(codeResult).get("openid"));
        Map<String,String> user = weiXinService.getUserInfo(openId);
//        System.out.println(openId);
        //判断是否该用户是否已经存在
        if(user==null||user.isEmpty()){
            System.out.println("获取");
            String access_token = String.valueOf(JsonUtil.stringToCollect(codeResult).get("access_token"));
            //获取用户信息
            String url1 = " https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
            String result1 = CurlUtil.getContent(url1, null, "GET");
            Map<String,String> userInfo = JsonUtil.stringToCollect(result1);
            System.out.println(userInfo.toString());
            boolean res = weiXinService.insertUserInfo(userInfo);
            if(!res){
                String str = "获取用户信息失败";
                resp.setContentType("text/JavaScript; charset=utf-8");
                resp.getWriter().println(str);
                return;
            }
            user.put("openId",userInfo.get("openid"));
            user.put("headImgUrl",userInfo.get("headimgurl"));
            user.put("nickName",userInfo.get("nickname"));
        }
        //设置session
        HttpSession session = req.getSession();
        session.setAttribute("User",user);

        // 设置响应内容类型
        resp.setContentType("text/html;charset=UTF-8");

        // 要重定向的新位置
        String site = new String(Const.startPageUrl);
        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
        resp.setHeader("Location", site);
        return;
    }
}
