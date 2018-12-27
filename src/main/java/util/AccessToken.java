package util;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

import java.sql.*;

public class AccessToken {

    public static  String getAccessToken() {
        String accessToken = null;
        try {
            accessToken = getAccessTokenFromDatabase();
            if (accessToken == null) {
                synchronized (AccessToken.class) {
                    if (getAccessTokenFromDatabase() == null) {
                        accessToken = curlForAccessToken();
                        System.out.println(accessToken);
                        Jedis jedis = new Jedis("localhost");
                        jedis.set("access_token",accessToken);
                        jedis.expire("access_token",7200);
                        jedis.close();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    private static String getAccessTokenFromDatabase() throws SQLException, ClassNotFoundException {
        //access_token timestamp过期时间
        Jedis jedis = new Jedis("localhost");
    String accessToken = jedis.get("access_token");
        return accessToken;
                }

private static String curlForAccessToken() {
        String appId = Const.AppId;
        String appSecret = Const.AppSecret;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        String result = CurlUtil.getContent(url, null, "GET");
        JSONObject data = JSONObject.parseObject(result);
        String accessToken = (String) data.get("access_token");
        return accessToken;
        }
}
