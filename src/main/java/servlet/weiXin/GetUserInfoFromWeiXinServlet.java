package servlet.weiXin;

import Imp.WeiXinServiceImp;
import service.WeiXinService;

import util.Const;
import util.JsonUtil;
import util.StringUtil;
import util.UrlUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:21 2018/4/10
 */
public class GetUserInfoFromWeiXinServlet extends HttpServlet {
    static WeiXinService weiXinService = new WeiXinServiceImp();

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //获取用户toekn
//        String code = req.getParameter("code");
//        String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Const.AppId + "&secret=" + Const.AppSecret + "&code=" + code + "&grant_type=authorization_code";
//        String codeResult = CurlUtil.getContent(codeUrl, null, "GET");
//        String openId = String.valueOf(JsonUtil.stringToCollect(codeResult).get("openid"));
//        Map<String, String> user = weiXinService.getUserInfo(openId);
////        System.out.println(openId);
//        //判断是否该用户是否已经存在
//        if (user == null || user.isEmpty()) {
//            System.out.println("获取");
//            String access_token = String.valueOf(JsonUtil.stringToCollect(codeResult).get("access_token"));
//            //获取用户信息
//            String infoUrl = " https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId + "&lang=zh_CN";
//            String infoResult = CurlUtil.getContent(infoUrl, null, "GET");
//            System.out.println(infoResult);
//            user = JsonUtil.stringToCollect(infoResult);
//            System.out.println(user);
//            boolean res = weiXinService.insertUserInfo(user);
//            if (!res) {
//                String str = "获取用户信息失败";
//                resp.setContentType("text/JavaScript; charset=utf-8");
//                resp.getWriter().println(str);
//                return;
//            }
//        }
//        //设置session
//        HttpSession session = req.getSession();
//        session.setAttribute("User", user);
//        // 设置响应内容类型
//        resp.setContentType("text/html;charset=UTF-8");
//        // 要重定向的新位置
//        String site = new String(Const.startPageUrl);
//        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
//        resp.setHeader("Location", site);
//        return;
//    }

    public void  doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Map user = (Map) session.getAttribute("User");
        if (user == null || user.isEmpty()) {
            String openid = request.getParameter("openid");
            String nickname = request.getParameter("nickname");
            String imgurl = request.getParameter("headimgurl");
                if (StringUtil.hasBlank(openid, nickname, imgurl)) {
                    String redirectUrl = Const.user_info + UrlUtil.getURLEncoderString(Const.redirect_uri);
                    response.sendRedirect(redirectUrl);
                    return;
                }
            nickname = UrlUtil.getURLDecoderString(nickname);
            imgurl = UrlUtil.getURLDecoderString(imgurl);
            openid =UrlUtil.getURLDecoderString(openid);
            Map<String,String> userInfo = new HashMap<>();
            userInfo.put("openId",openid);
            userInfo.put("headImgUrl",imgurl);
            userInfo.put("nickName",nickname);
            session.setAttribute("User",userInfo);
            boolean res = weiXinService.insertUserInfo(userInfo);
            if(!res){
                String str = "false";
                response.setContentType("text/JavaScript; charset=utf-8");
                response.getWriter().println(str);
            }

        }
        return ;
    }
}





