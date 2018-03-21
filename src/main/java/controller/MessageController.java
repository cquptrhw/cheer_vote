package controller;

import model.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageController {
    //上传留言
    public String updateMessage(String openId,String content,String classId) throws JSONException {
        //连接数据库
        Connection connection = null;


        try {
            connection = Mysql.getConnection();
            if (connection == null) {
                System.out.println("数据库连接失败");
            }
            String insertSql = "insert into content_users (openId,content,classId) values(?,?,?)";
            PreparedStatement statement1 = connection.prepareStatement(insertSql);
            statement1.setString(1,openId);
            statement1.setString(2,content);
            statement1.setString(3,classId);
            Boolean status = statement1.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("message","OK");
        result.put("status",200);
        String str = result.toString();
        return str;


    }
    //留言点赞
    public String praiseMessage(String openId,String contentId) throws JSONException {
        //连接数据库
        Connection connection = null;
        try {
            connection = Mysql.getConnection();
            if (connection == null) {
                System.out.println("数据库连接失败");
            }
            String insertSql = "insert into content_praise (openId,contentId) values(?,?)";
            PreparedStatement statement1 = connection.prepareStatement(insertSql);
            statement1.setString(1,openId);
            statement1.setString(2,contentId);
            Boolean status = statement1.execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("message","OK");
        result.put("status",200);
        String str = result.toString();
        return str;


    }
    //获取留言
    public String getMessage(int page, int classId) throws SQLException, JSONException {
        JSONArray messageArray = new JSONArray();
        JSONObject result = new JSONObject();

        //实例化Message
        Message message = new Message();
        //页数，页码
        int pageSize = 10;
        int startPage = (page -1) * pageSize;
        //连接数据库
        Connection connection = null;
        try {
            connection = Mysql.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //查询语句
        String contentsql="select * from content_users where classId = ? order by created_time desc LIMIT ?,?";
        String usersql = "select * from user_info where openId = ? ";
        String praiseSql = "select count(id) AS Num from content_praise where contentId = ?";




        PreparedStatement ptmt= null;
        PreparedStatement ptmt1 =null;
        PreparedStatement ptmt2 =null;

        ptmt = connection.prepareStatement(contentsql);
        ptmt.setInt(1,classId);
        ptmt.setInt(2,startPage);
        ptmt.setInt(3,pageSize);
        ResultSet rs=ptmt.executeQuery();
        while (rs.next()){
            //获取留言信息
            String openId = rs.getString("openId");
            message.setContentId(rs.getInt("contentId"));
            message.setContent(rs.getString("content"));
            message.setCreate_time(rs.getString("created_time"));
            //获取发布者信息
            ptmt1 = connection.prepareStatement(usersql);
            ptmt1.setString(1,openId);
            ResultSet rs1=ptmt1.executeQuery();
            while(rs1.next()){
                message.setNickname(rs1.getString("nickName"));
                message.setHeadImgUrl(rs1.getString("headImgUrl"));
            }
            //获取留言点赞数
            ptmt2 = connection.prepareStatement(praiseSql);
            ptmt2.setInt(1,message.getContentId());
            ResultSet rs2=ptmt2.executeQuery();
            while (rs2.next()){
                message.setPraiseNum(rs2.getInt("Num"));
            }
            JSONObject json = new JSONObject();
            json.put("contentId",message.getContentId());
            json.put("content",message.getContent());
            json.put("nickname",message.getNickname());
            json.put("create_time",message.getCreate_time());
            json.put("headImgUrl",message.getHeadImgUrl());
            json.put("prasieNum",message.getPraiseNum());
            messageArray.put(json);
        }
        result.put("data",messageArray);
        result.put("message","OK");
        result.put("status",200);
        String str = result.toString();
        return str;
    }

    //主函数测试
//    public static void main(String[] args) throws SQLException, JSONException {
//        MessageController messageController = new MessageController();
//        String str = messageController.getMessage(1,1);
//        System.out.println(str);
//    }

}
