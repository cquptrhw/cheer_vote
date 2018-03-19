package controller;//package controller;
//
//import Imp.AdminServiceImp;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import service.AdminService;
//import util.Mysql;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class AdminController {
//    private AdminService adminService = new AdminServiceImp();
//
//    //管理员登陆
////    public String adminLogin(String username, String password,HttpSession session) {
////        JsonUtil jsonUtil = new JsonUtil();
////        String str = null;
////        //查询session中的admin
////        Object user = session.getAttribute("IAdmin");
////        if(user != null){
////            str = jsonUtil.toJSONString(user);
////        }else{
////            //重新登录
////            MyAdmin admin = adminService.adminLogin(username,password);
////            if(admin != null){
////                session.setAttribute("admin",admin);
////                str = jsonUtil.toJSONString(admin);
////            }
////        }
////
////        return str;
////    }
//    //上传啦啦队员信息
////    public String updateCheerPlayerInfo(Cheer_player cheer_player){
////        adminService.updateCheerPlayerInfo(cheer_player);
////        return "ok";
////
////    }
//    //上传啦啦队信息
////    public String updateCheerInfo(String classId,Map textMap,Map fileMap) throws SQLException {
////        //连接数据库
////        Connection connection = null;
////        String updateSql = null;
////        String insertSql = null;
////        PreparedStatement statement1 = null;
////        PreparedStatement statement2 = null;
////        try {
////            connection = Mysql.getConnection();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        //关闭自动提交
////        connection.setAutoCommit(false);
////        //遍历啦啦队基本信息表
////        Iterator iteror = textMap.entrySet().iterator();
////        Iterator iteror2 = fileMap.entrySet().iterator();
////        //插入啦啦队基本信息
////        while (iteror.hasNext()) {
////                Map.Entry entry = (Map.Entry) iteror.next();
////            Object key = entry.getKey();
////            Object value = entry.getValue();
////            //控制台打印
////           // System.out.println(key + ":" + value);
////                updateSql = "update cheerInfo set "+key+"=? where classId=? ";
////                statement1 = connection.prepareStatement(updateSql);
////                statement1.setString(1, (String) value);
////
////                statement1.setString(2, classId);
////                statement1.executeUpdate();
////        }
////        //插入啦啦队的文件地址
////        while (iteror2.hasNext()) {
////            Map.Entry entry = (Map.Entry) iteror2.next();
////            Object type = entry.getKey();
////            Object url = entry.getValue();
////            //控制台打印
////            //System.out.println(type + ":" + url);
////            insertSql = "insert into cheerFile (classId,type,url) values(?,?,?)";
////            statement2 = connection.prepareStatement(insertSql);
////            statement2.setString(1, classId);
////            statement2.setString(2, (String) type);
////            statement2.setString(3, (String) url);
////            statement2.executeUpdate();
////        }
////        //更改啦啦队文件上传状态
////        updateSql = "update cheerInfo set result = 1 where classId=? ";
////        statement1 = connection.prepareStatement(updateSql);
////        statement1.setString(1, classId);
////        statement1.executeUpdate();
////        //事务提交
////        try{
////            connection.commit();
////        }catch(SQLException se){
////            connection.rollback();
////        }
////        //上传成功
////        String message = "OK";
////        return message;
////
////    }
//    //查询啦啦队否上传信息
////    public String IsUpdateCheerInfo(HttpSession session) throws SQLException, JSONException {
////        String str =null;
////        Object user = session.getAttribute("admin");
////        if(user == null){
////            str = "请先登录";
////        }else{
////            List<Cheer_result> cheer_results = adminService.getCheerResult();
////
////            if(cheer_results.isEmpty()){
////                str="查询错误";
////            }else {
////                JsonUtil jsonUtil = new JsonUtil();
////                str = jsonUtil.listToJsonArray(cheer_results);
////                if(str != null || str.isEmpty()){
////                    return str;
////                }
////            }
////        }
////
////        return str ;
////    }
//    //题目上传
////    public  String updateQuestion(Question question){
////        Connection connection = null;
////        try {
////            connection = Mysql.getConnection();
////            if (connection == null) {
////                System.out.println("数据库连接失败");
////            }
////            String insertSql = "insert into question (question,A,B,C,D,answer,kind) values(?,?,?,?,?,?,?)";
////            PreparedStatement statement1 = connection.prepareStatement(insertSql);
////            statement1.setString(1,question.getTitle());
////            statement1.setString(2,question.getA());
////            statement1.setString(3, question.getB());
////            statement1.setString(4, question.getC());
////            statement1.setString(5,question.getD());
////            statement1.setString(6,question.getAnswer());
////            statement1.setString(7,question.getKind());
////            Boolean status = statement1.execute();
////            } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        String str = "OK";
////        return str;
////    }
////    //获取题目数量以及管理员权限信息
////    public String getQuestionNum(String username) throws SQLException, JSONException {
////        int role = 0;
////        JSONArray arrayNum = new JSONArray();
////        JSONObject result = new JSONObject();
////        //连接数据库
////        Connection connection = null;
////        try {
////            connection = Mysql.getConnection();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        String rolesql="select * from admin where username = ? and role = ?";
////        PreparedStatement ptmt= null;
////        ptmt = connection.prepareStatement(rolesql);
////        ptmt.setString(1,username);
////        ptmt.setInt(2,1);
////        ResultSet rs=ptmt.executeQuery();
////        if(rs.next()){
////            role = 1;
////        }else{
////            role = 0;
////        }
////
////        String numsql="select kind, count(question) AS Num from question group by kind";
////        PreparedStatement ptmt1= null;
////        ptmt1 = connection.prepareStatement(numsql);
////        ResultSet rs1=ptmt1.executeQuery();
////        while(rs1.next()){
////            JSONObject json1 = new JSONObject();
////            json1.put("kind",rs1.getString("kind"));
////            json1.put("total",rs1.getInt("Num"));
////            arrayNum.put(json1);
////        }
////        result.put("data",arrayNum);
////        result.put("role",role);
////        result.put("message","OK");
////        result.put("status",200);
////        String str = result.toString();
////        return str;
////    }
////    //查看已经上传的题目
////    public String getQuestionList(String username , String kind) throws SQLException, JSONException {
////        JSONArray questionArray = new JSONArray();
////        JSONObject result = new JSONObject();
////        //连接数据库
////        Connection connection = null;
////        try {
////            connection = Mysql.getConnection();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        String rolesql="select * from admin where username = ? and role = ?";
////        PreparedStatement ptmt= null;
////        ptmt = connection.prepareStatement(rolesql);
////        ptmt.setString(1,username);
////        ptmt.setInt(2,1);
////        ResultSet rs=ptmt.executeQuery();
////        if(rs.next()){
////            String listsql="select * from question where kind = ?";
////            PreparedStatement ptmt1= null;
////            ptmt1 = connection.prepareStatement(listsql);
////            ptmt1.setString(1,kind);
////            ResultSet rs1=ptmt1.executeQuery();
////            while(rs1.next()){
////                JSONObject json1 = new JSONObject();
////                json1.put("questionId",rs1.getString("questionId"));
////                json1.put("question",rs1.getString("question"));
////                json1.put("A",rs1.getString("A"));
////                json1.put("B",rs1.getString("B"));
////                json1.put("C",rs1.getString("C"));
////                json1.put("D",rs1.getString("D"));
////                json1.put("answer",rs1.getString("answer"));
////
////                questionArray.put(json1);
////            }
////            result.put("data",questionArray);
////            result.put("message","OK");
////            result.put("status",200);
////            String str = result.toString();
////            return str;
////        }else{
////            result.put("message","无权限");
////            result.put("status",200);
////            String str = result.toString();
////            return str;
////        }
////
////
////    }
////    //删除题目
//    public String delQuestionById(String username ,String questionId) throws SQLException, JSONException {
//        Connection connection = null;
//        JSONObject result = new JSONObject();
//        String message = null;
//        try {
//            connection = Mysql.getConnection();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        String rolesql="select * from admin where username = ? and role = ?";
//        PreparedStatement ptmt= null;
//        ptmt = connection.prepareStatement(rolesql);
//        ptmt.setString(1,username);
//        ptmt.setInt(2,1);
//        ResultSet rs=ptmt.executeQuery();
//        if(rs.next()){
//            String delsql="DELETE  from question where questionId = ?";
//            PreparedStatement ptmt1= null;
//            ptmt1 = connection.prepareStatement(delsql);
//            ptmt1.setString(1,questionId);
//            int i=ptmt1.executeUpdate();
//            if(i==0){
//                    message = "false";
//            }else {
//                 message = "OK";
//            }
//            result.put("message",message);
//            result.put("status",200);
//            String str = result.toString();
//            return str;
//
//        }else{
//            result.put("message","无权限");
//            result.put("status",200);
//            String str = result.toString();
//            return str;
//        }
//    }
//
//
//}
//
//
