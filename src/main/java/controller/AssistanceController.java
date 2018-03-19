package controller;

import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import util.Mysql;
import util.Redis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class AssistanceController {
    //获取剩余助力数目
    public String getAssistanceByOpenId(String openId) throws SQLException, JSONException {
        String openAssistance = openId+"Assistance";
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        String assistance =  jedis.get(openAssistance);
        //如果缓存不存在则从数据库查询并进行缓存
        if(assistance==null||assistance.isEmpty()){
            Connection connection = null;
            try {
                connection = Mysql.getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String assistanceSql="select assistance from user_assistance WHERE  binary openId = ?";
            PreparedStatement ptmt = connection.prepareStatement(assistanceSql);
            ptmt.setString(1, openId);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                assistance = rs.getString("assistance");
            }else{
                assistance = "0";
            }
            JSONObject assistanceJson = new JSONObject();
            assistanceJson.put("status",200);
            assistanceJson.put("message","OK");
            assistanceJson.put("assistance",assistance);
            String assisJson = assistanceJson.toString();
            jedis.set(openAssistance, assisJson);
            return assisJson;
        }
        jedis.close();
        return assistance;
    }

    //为啦啦队增加助力数
    public String updateCheerAssistance(Map map,String openId,int acount) throws SQLException, JSONException {
        String str =null;
        JSONObject jsonObject = new JSONObject();
        Connection connection = null;
        String insertSql = null;
        String updateSql = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        //删除缓存
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        jedis.del(openId+ "Assistance");
        jedis.close();

        try {
            connection = Mysql.getConnection();
            //设置JDBC事务
            connection.setAutoCommit(false);
            //遍历HashMap
            Iterator iteror = map.entrySet().iterator();
            while (iteror.hasNext()) {
                //遍历HashMap获得classId 和 assistance
                Map.Entry entry = (Map.Entry) iteror.next();
                Object classId = entry.getKey();
                Object num = entry.getValue();
                //将助力历史插入数据库
                insertSql = "insert into cheer_assistance (openId,assistance,classId) values(?,?,?) ";
                statement1 = connection.prepareStatement(insertSql);
                statement1.setString(1,openId);
                statement1.setString(2, (String) num);
                statement1.setString(3, (String) classId);
                statement1.executeUpdate();
            }
            updateSql = "update user_assistance set assistance = assistance - ? where openId = ? ";
            statement2 = connection.prepareStatement(updateSql);
            statement2.setInt(1,acount);
            statement2.setString(2,openId);
            statement2.execute();
            connection.commit();
            jsonObject.put("status",200);
            jsonObject.put("message","OK");
            str = jsonObject.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            jsonObject.put("status",400);
            jsonObject.put("message","提交错误");
            str = jsonObject.toString();
        }finally {
            //重新加入缓存
            getAssistanceByOpenId(openId);
            return str;
        }


    }

    //判断是否超过自己具有的助力数目
    public boolean isOverAssistance(String openId,int num) throws SQLException, JSONException {
        //获取用户剩余的assistance
        String str = getAssistanceByOpenId(openId);
        //判断是否超过
        com.alibaba.fastjson.JSONObject userAssistance;
        userAssistance = JSON.parseObject(str);
        int assistance1 = userAssistance.getInteger("assistance");
        if(assistance1 >= num && num != 0){
            return true;
        }else{
            return false;
        }
    }

    //获取助力历史
    public String getAssistanceHistoryByOpenId(String openId) throws SQLException, ClassNotFoundException, JSONException {
        String str = null;
            JSONArray jsonArray = new JSONArray();
            JSONObject assistanceHistory = new JSONObject();
            //数据库查询
            Connection connection = Mysql.getConnection();
            String sql = "select classId,count(assistance) as Num from cheer_assistance where openId = ?  group by classId order by Num desc ";
            PreparedStatement ptmt = null;
            ptmt = connection.prepareStatement(sql);
            ptmt.setString(1,openId);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()){
                String classId = rs.getString("classId");
                int num = rs.getInt("Num");
                int assistance = 0;
                //查询班级的总里程
                String sql1 = "select count(assistance) as distance from cheer_assistance where classId = ?";
                PreparedStatement ptmt1= null;
                ptmt1 = connection.prepareStatement(sql1);
                ptmt1.setString(1,classId);
                ResultSet rs1 = ptmt1.executeQuery();

                while (rs1.next()){
                    assistance = rs1.getInt("distance");
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("classId",rs.getString("classId"));
                jsonObject.put("assistance",rs.getString("Num"));
                jsonObject.put("distance",assistance);
                jsonArray.put(jsonObject);
//                jsonObject.put("create_time",rs.getTimestamp("create_time"));

            }
            assistanceHistory.put("data",jsonArray);
            assistanceHistory.put("status",200);
            assistanceHistory.put("message","OK");
            str = assistanceHistory.toString();

        return str;
    }
    public static void main(String[] args) throws SQLException, JSONException, ClassNotFoundException {
            AssistanceController assistanceController =new AssistanceController();
            String str = assistanceController.getAssistanceHistoryByOpenId("aaa");
            System.out.println(str);
    }


}
