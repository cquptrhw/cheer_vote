//package Listener;
//
//import controller.InfoController;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import util.JedisUtil;
//import util.Mysql;
//import util.Time;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import java.sql.*;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class luckUser implements ServletContextListener {
//
//    @Override
//    public void contextDestroyed(ServletContextEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }
//    //tomcat启动后就会执行该方法
//    @Override
//    public void contextInitialized(ServletContextEvent arg0) {
//        System.out.println("tomcat listener start");
//
//        //连接Redis
//        final JedisUtil jedis = new JedisUtil();
//        //设置run方法的延迟启动时间
//        final Time time = new Time();
//        long diff= time.getTimeDiff();
//        System.out.println(diff);
//        //设置自启方法 晚上八点获取幸运用户
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            public void run() {
//                Timestamp[]timeArray = time.getTimePeriod(); //得到幸运用户选取的时间段
//                InfoController infoController = new InfoController();//获取用户昵称和头像信息
//                JSONArray userArray = new JSONArray();
//                JSONObject userLuck = new JSONObject();
//
//                Connection connection = null;
//                try {
//                    connection = Mysql.getConnection();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
////                String sql = "select openId,count(result = 1)  AS Num from question_history group by openId ORDER BY RAND() LIMIT 3"; //获取用户分数
//
//                String sql ="SELECT openId FROM question_history WHERE create_time between ? and ? GROUP BY openId ORDER BY RAND() LIMIT 3";
//                PreparedStatement ptmt = null;
//                try {
//                    ptmt = connection.prepareStatement(sql);
//                    ptmt.setTimestamp(1,timeArray[0]);
//                    ptmt.setTimestamp(2,timeArray[1]);
//                    ResultSet rs = ptmt.executeQuery();
//                    while (rs.next()){
//                        JSONObject user = new JSONObject();
//                        Map map = infoController.getUserInfoByOpenID(rs.getString("openId"));
////                        System.out.println(rs.getString("openId"));
//                        user.put("nickName",map.get("nickName"));
//                        user.put("headImgUrl",map.get("headImgUrl"));
////                        user.put("Num",rs.getString("Num"));
////                        System.out.println(user.toString());
//                        userArray.put(user);
//
//                        String historySql = "insert into user_luck (openId,nickName,headImgUrl) value (?,?,?)";
//                        PreparedStatement hisment = connection.prepareStatement(historySql);
//                        hisment.setString(1,rs.getString("openId"));
//                        hisment.setString(2, (String) map.get("nickName"));
//                        hisment.setString(3, (String) map.get("headImgUrl"));
//                        int sta =  hisment.executeUpdate();
//                        if(sta!=1){
//                            System.out.println("幸运用户插入失败");
//                        }
//                    }
//                    userLuck.put("status",200);
//                    userLuck.put("message","OK");
//                    userLuck.put("data",userArray);
//                    String str = userLuck.toString();
//                    jedis.setString("luckUser",str);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, diff, 24*60*60*1000);
//    }
//
//}
