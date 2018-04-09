package servlet;

import com.alibaba.fastjson.JSONObject;
import util.Const;
import util.CurlUtil;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:21 2018/4/10
 */
public class GetUserInfoFromWeiXinServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户toekn
        String code = req.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ Const.AppId+"&secret="+Const.AppSecret+"&code="+code+"&grant_type=authorization_code";
        String result = CurlUtil.getContent(url, null, "GET");
        System.out.println(result);
        String openId = String.valueOf(JsonUtil.stringToCollect(result).get("openId"));
        String access_token = String.valueOf(JsonUtil.stringToCollect(result).get("access_token"));
        //获取用户信息
        System.out.println(openId);
        System.out.println(access_token);
        String url1 = " https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId+"&lang=zh_CN";
        String result1 = CurlUtil.getContent(url1, null, "GET");
        System.out.println(result1);


        //获取用户信息


    }
}
