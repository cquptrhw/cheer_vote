package controller;

import util.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class InfoController {
    public Map getUserInfoByOpenID(String openId){
        Map user = new HashMap();
        Connection connection = null;
        try {
            connection = Mysql.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select nickName ,headImgUrl from user_info where openId = ?";
        PreparedStatement ptmt = null;
        try {
            ptmt = connection.prepareStatement(sql);
            ptmt.setString(1,openId);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()){
                user.put("nickName",rs.getString("nickName"));
                user.put("headImgUrl",rs.getString("headImgUrl"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
//    public static void main(String[] args) {
////        InfoController infoController = new InfoController();
////        Map map = infoController.getUserInfoByOpenID("aaaa");
////        System.out.println(map.get("nickName"));
//        String str = "[{name:'a',value:'aa'},{name:'b',value:'bb'},{name:'c',value:'cc'},{name:'d',value:'dd'}]" ;  // 一个未转化的字符串
//
//        JSONArray json = JSONArray.parseArray(str ); // 首先把字符串转成 JSONArray  对象
//
//        if(json.size()>0){
//            for(int i=0;i<json.size();i++){
//                JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//                System.out.println(job.get("name")+"="+job.get("value")) ;  // 得到 每个对象中的属性值
//            }
//        }
//    }
}
