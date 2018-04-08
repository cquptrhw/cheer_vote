package org.redrock.util;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;

public class AccessToken {

    public static   String getAccessToken() {
        String accessToken = null;
        try {
            accessToken = getAccessTokenFromDatabase();
            if (accessToken == null) {
                synchronized (AccessToken.class) {
                    if (getAccessTokenFromDatabase() == null) {
                        accessToken = curlForAccessToken();
                        System.out.println(accessToken);
                        int timestamp = (int) (System.currentTimeMillis() / 1000);
                        timestamp = timestamp + 7200;
                        Connection connection = Mysql.getConnection();
                        String updateSql = "update access_token set access_token = ?, timestamp = ? WHERE id = 1";
                        PreparedStatement statement1 = connection.prepareStatement(updateSql);
                        statement1.setString(1, accessToken);
                        statement1.setInt(2, timestamp);
                        boolean status = statement1.execute();
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
        Connection connection = Mysql.getConnection();
        if (connection == null) {
            System.out.println("数据库连接失败");
        }
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        String sql = "select * from access_token where timestamp > " + timestamp;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        String accessToken = null;
        while (resultSet.next()) {
            accessToken = resultSet.getString("access_token");
        }
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
