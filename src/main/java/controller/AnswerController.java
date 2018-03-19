package controller;

import com.alibaba.fastjson.JSON;
import dto.Question;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import util.Mysql;
import util.Redis;
import util.Time;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnswerController {
    //当天第一次答题时
    public String getNewQuestion(String openId) throws SQLException, JSONException {
        int i=0;
        int j=0;
        Time time =new Time();
        String openQuestion = openId+"Question";
        String opentodayNum = openId+"todayNum";
        String questionAnswer = "questionAnswer";
        String answer = null;
        Question question = new Question();
        JSONObject result = new JSONObject();

        //连接数据库
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        Connection connection = null;
        try {
            connection = Mysql.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String rolesql="select * from question WHERE kind =? ORDER BY RAND() LIMIT 10";
        PreparedStatement ptmt= null;
        ptmt = connection.prepareStatement(rolesql);
        for(i=0;i<=5;i++) {  //循环遍历五种难度
            ptmt.setInt(1, i);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                j++;
                int questionId = rs.getInt("questionId");
                JSONObject json = new JSONObject();
                question.setTitle(rs.getString("question"));
                question.setA( rs.getString("A")); ;
                question.setB(rs.getString("B"));
                question.setC(rs.getString("C"));
                question.setD(rs.getString("D"));
                question.setAnswer(rs.getString("answer"));

                //获取题目
                json.put("questionId",questionId);
                json.put("question",question.getTitle());
                json.put("A",question.getA());
                json.put("B",question.getB());
                json.put("C",question.getC());
                json.put("D",question.getD());

                //获取答案
                answer = rs.getString("answer");

                //将题目和答案存入redis
                result.put("todayNum",j);
                result.put("data",json);
                result.put("message","OK");
                result.put("status",200);
                String str = result.toString();
                jedis.hset(openQuestion,"key"+j,str);
                jedis.hsetnx(questionAnswer, String.valueOf(questionId),answer);
            }
        }
        //设置今日答题数
        jedis.set(opentodayNum,"1");
        //给随机出来的题目和今日答题数设置过期时间
        long diff = time.getTimeDiff();//
        jedis.pexpire(openQuestion,diff);
        jedis.pexpire(opentodayNum,diff);
        jedis.pexpire(questionAnswer,diff);
        //返回题目
        String str = jedis.hget(openQuestion,"key1");
        jedis.close();
        return str;
    }

    //判断是否为当天第一次答题
    public int isAnswer(String openId){
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        String opentodayNum = openId+"todayNum";
        int num = 0;
        String rs = jedis.get(opentodayNum);
        jedis.close();
        if(rs == null || rs.length() <= 0){
            return num;
        }else{
            num = Integer.parseInt(rs);
            return num;
        }

    }

    //当天已经答过题继续答题
    public String getOldQuestion (String openId ,int todayNum) throws SQLException, JSONException {
        Time time =new Time();
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        String openQuestion = openId+"Question";
        String opentodayNum = openId+"todayNum";
        int num = todayNum+1;
        //获取题目
        String str = jedis.hget(openQuestion,"key"+num);
        //获取题目失败 则从mysql重新获取
        if(str==null ||str.isEmpty()){
            str = getNewQuestion(openId);
        }
        //更新今日答题数
        jedis.set(opentodayNum, String.valueOf(num));
        //设置过期时间
        long diff = time.getTimeDiff();
        jedis.pexpire(opentodayNum,diff);
        return str;
    }

    //获取答案
    public String getAnswer(String openId,String openanswer,String questionId) throws JSONException {
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        String openQuestion = openId+"Question";
        String opentodayNum = openId+"todayNum";
        String openAnswer = openId + "Answer";
        String questionAnswer ="questionAnswer";
        int result =0;
        String answer = jedis.hget(questionAnswer,questionId);
        JSONObject jsonObject = new JSONObject();
        if(answer.equals(openanswer)){
            result=1;
        }else {
            result = 0;
        }
        jsonObject.put("answer",answer);
        jsonObject.put("openanswer",openanswer);
        jsonObject.put("result",result);
        String str = jsonObject.toString();
        jedis.hset(openAnswer,questionId,str);
        ;
        //将该题目正确答案回传
        JSONObject jsonAnswer = new JSONObject();
        jsonAnswer.put("status",200);
        jsonAnswer.put("message","OK");
        jsonAnswer.put("answer",answer);
        String str1 = jsonAnswer.toString();
        return str1;
    }

    //结束答题
    public String endQuestion(String openId) throws SQLException, JSONException {
        String openAnswer = openId + "Answer";
        String openAssistance = openId +"Assistance";
        Map map = new HashMap();

        //连接数据库
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        Connection connection = null;
        try {
            connection = Mysql.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //查询分数
        String rolesql="select * from user_assistance WHERE binary openId = ?";//openId区分大小写查询
        PreparedStatement ptmt = connection.prepareStatement(rolesql);
        //插入分数
        String insertSql = "insert into user_assistance (openId,assistance)VALUE (?,?)";
        PreparedStatement statement1 = connection.prepareStatement(insertSql);
        //更新分数
        String updateSql = "update user_assistance set assistance = ? where openId =?";
        PreparedStatement updatement = connection.prepareStatement(updateSql);
        //插入答题记录
        String historySql = "insert into question_history (openId,questionId,answer,openanswer,result) value (?,?,?,?,?)";
        PreparedStatement hisment = connection.prepareStatement(historySql);
        synchronized (this){
            int assistance = 0;
            int num =0; //此次答题得分
            int exist=0; //此前是否有助力数
            //查询之前剩余的助力数
            ptmt.setString(1, openId);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()){
                exist = 1;
                assistance = rs.getInt("assistance");
            }else{
                exist = 0;
                assistance = 0;
            }

            //获取这次答题的情况
            map= jedis.hgetAll(openAnswer);
            if(map == null || map.isEmpty()){  //如果没有答题，直接结束答题
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status",200);
                jsonObject.put("message","OK");
                jsonObject.put("assistance",assistance);
                String str = jsonObject.toString();
                return  str;
            }else{                              //有答题记录
                //遍历HashMap
                Iterator iteror = map.entrySet().iterator();
                while (iteror.hasNext()) {
                    //获取questionId和答题记录
                    Map.Entry entry = (Map.Entry) iteror.next();
                    Object questionId = entry.getKey();
                    Object value = entry.getValue();
//                System.out.println(questionId + ":" + value);
                    //获取答题记录
                    com.alibaba.fastjson.JSONObject userAnswer;
                    userAnswer = JSON.parseObject(String.valueOf(value));
                    String answer = userAnswer.getString("answer");
                    String openanswer= userAnswer.getString("openanswer");
                    String result = userAnswer.getString("result");
//                System.out.println(answer);
//                System.out.println(openanswer);
//                System.out.println(result);
                    //将答题历史插入数据库
                    hisment.setString(1,openId);
                    hisment.setString(2, String.valueOf(questionId));
                    hisment.setString(3,answer);
                    hisment.setString(4,openanswer);
                    hisment.setString(5,result);
                    int sta =  hisment.executeUpdate();
                    if(sta!=1){
                        System.out.println("答题历史插入失败");
                    }
                    if(result.equals("1")){
                        num++;
                    }
                }
                assistance = assistance + num;
                //如果数据库存在数据就更新，不存在就插入数据
                if(exist==0){
                    statement1.setString(1,openId);
                    statement1.setInt(2,assistance);
                    int status = statement1.executeUpdate();
                    if(status!=1){
                        System.out.println("插入助力数失败");
                    }
                }else{
                    updatement.setString(2,openId);
                    updatement.setInt(1,assistance);
                    updatement.executeUpdate();
                }
                //修改Redis的缓存助力数
                JSONObject assistanceJson = new JSONObject();
                assistanceJson.put("status",200);
                assistanceJson.put("message","OK");
                assistanceJson.put("assistance",assistance);
                String str = assistanceJson.toString();
                jedis.set(openAssistance, str);

//                jsonObject.put("status",200);
//                jsonObject.put("message","OK");
//                jsonObject.put("assistance",assistance);
//                String str = jsonObject.toString();
                //删除答题缓存
                jedis.del(openAnswer);
                //删除答题排行榜
                jedis.del("userList");
                jedis.close();
                return str;
            }

        }


    }
    //获取幸运用户
    public String getLuckUser () throws JSONException, SQLException {
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        String userList = jedis.get("luckUser");
        if(userList==null||userList.isEmpty()){
            JSONArray userArray = new JSONArray();
            JSONObject userLuck = new JSONObject();
            Connection connection = null;
            try {
                connection = Mysql.getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String assistanceSql="select * from user_luck ORDER by create_time  Limit 3";
            PreparedStatement ptmt = connection.prepareStatement(assistanceSql);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()){
                JSONObject user = new JSONObject();
                user.put("nickName",rs.getString("nickName"));
                user.put("headImgUrl",rs.getString("headImgUrl"));
                userArray.put(user);
            }
            userLuck.put("status",200);
            userLuck.put("message","OK");
            userLuck.put("data",userArray);
            String str = userLuck.toString();
            jedis.set("luckUser",str);
            return str;
        }
        jedis.close();
        return  userList;
    }

    //获取答题榜单
    public String getUserList() throws SQLException, JSONException {
        Redis redis = new Redis();
        Jedis jedis = redis.getJedis();
        String str = jedis.get("userList");
        if(str==null||str.isEmpty()){
            Time time=new Time();
            Timestamp[]timeArray = time.getTimePeriodList();
            InfoController infoController = new InfoController();//获取用户昵称和头像信息
            JSONArray userArray = new JSONArray();
            JSONObject userLuck = new JSONObject();
            Connection connection = null;
            try {
                connection = Mysql.getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sql = "select openId,count(result = 1)  AS Num from question_history WHERE create_time between ? and ? group by openId ORDER by Num  DESC LIMIT 10"; //获取用户分数
            PreparedStatement ptmt = null;

            ptmt = connection.prepareStatement(sql);
            ptmt.setTimestamp(1,timeArray[0]);
            ptmt.setTimestamp(2,timeArray[1]);
//            System.out.println(timeArray[0]+"s"+timeArray[1]);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()){
                JSONObject jsonObject  = new JSONObject();
                Map map = infoController.getUserInfoByOpenID(rs.getString("openId"));
//                        System.out.println(rs.getString("openId"));
                jsonObject.put("nickName",map.get("nickName"));
                jsonObject.put("headImgUrl",map.get("headImgUrl"));
                jsonObject.put("todayNum",rs.getString("Num"));
                userArray.put(jsonObject);
            }
            userLuck.put("status",200);
            userLuck.put("message","OK");
            userLuck.put("data",userArray);
            str = userLuck.toString();
            jedis.set("userList",str);
            jedis.close();
            return str;
        }else{
            jedis.close();
            return str;
        }
    }


    public static void main(String[] args) throws SQLException, JSONException {
//        AnswerController answerController =new AnswerController();
////        String str = answerController.getNewQuestion("aaaaa");
//////        int str = answerController.isAnswer("aaaatodatNum");
////        String answer = answerController.getAnswer("aaaa","d","2");
////        System.out.println(str);
//
//
////        String jsonStr = "{ \"questionId\":\"1\", \"answer\":\"A\" ,\"status\": 200 } ";
////        com.alibaba.fastjson.JSONObject userAnswer = new com.alibaba.fastjson.JSONObject();
////        userAnswer = JSON.parseObject(jsonStr);
////        String str = userAnswer.getString("answer");
////        String questionId = userAnswer.getString("questionId");
////        System.out.println(str);
////        System.out.println(questionId);
////        Jedis jedis = new Jedis();
////        String lucklist = jedis.get("luckUser");
////        System.out.println("1"+lucklist);
//       String str =  answerController.getLuckUser();
//       System.out.println(str);


    }




}
