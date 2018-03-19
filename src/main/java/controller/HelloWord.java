package controller;//package controller;
//
//import java.io.Reader;
//import java.text.MessageFormat;
//import java.util.List;
//
//import dao.IAdmin;
//import model.Admin;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//
//public class HelloWord {
//    private static SqlSessionFactory sqlSessionFactory;
//    private static Reader reader;
//    static {
//        try {
//            reader = Resources.getResourceAsReader("config/Configure.xml");
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//            sqlSessionFactory.getConfiguration().addMapper(IAdmin.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static SqlSessionFactory getSession() {
//        return sqlSessionFactory;
//    }
//
//    public static void main(String[] args) {
//
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IAdmin iuser = session.getMapper(IAdmin.class);
//            Admin user = iuser.getUser("789","123");
//            System.out.println("名字："+user.getRole());
//            System.out.println("所属部门："+user.getUserId());
//            System.out.println("主页："+user.getPasswprd());
//        } finally {
//            session.close();
//        }
////        try {
////            SqlSession session = sqlSessionFactory.openSession();
////            IAdmin iuser = session.getMapper(IAdmin.class);
////            // 显示User信息
////            System.out.println("Test Get start...");
////            printUsers(iuser.getUserList());
////            System.out.println("Test Get finished...");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
////    private static void printUsers(final List<Admin> users) {
////        int count = 0;
////
////        for (Admin user : users) {
////            System.out.println(MessageFormat.format(
////                    "============= User[{0}]=================", ++count));
////            System.out.println("User Id: " + user.getPasswprd());
////            System.out.println("User Name: " + user.getUserId());
////            System.out.println("User Dept: " + user.getRole());
////        }
////    }
//}