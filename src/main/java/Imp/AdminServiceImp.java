package Imp;

import com.alibaba.fastjson.JSONException;
import dao.IAdmin;
import dto.Cheer_result;
import dto.MyAdmin;
import dto.Question;
import dto.Question_num;
import org.apache.ibatis.session.SqlSession;
import service.AdminService;
import util.JsonUtil;
import util.SqlSessionFactoryUtil;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:29 2018/3/14
 */
public  class AdminServiceImp implements AdminService {
     static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //管理员是否登录
    @Override
    public boolean isLogin(HttpSession session) {
        Object user = session.getAttribute("admin");
        if(user == null){
           return false;
        }else {
            return true;
        }
    }
    //是否为主管理员
    @Override
    public int isMainAdmin(HttpSession session) {
      Object user = session.getAttribute("admin");
        if(user == null){
            return 1;
        }else {
            Object role = session.getAttribute("role");
            if(role.equals(0)){
                return 2;
            }else {
                return 3;
            }
        }

    }

    @Override
    //管理员登录
    public MyAdmin adminLogin(String username, String password) {
        MyAdmin user;
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            user = iuser.getUser(username,password);
        } finally {
            session.close();
        }
        return user;
    }

    //查询啦啦队信息是否上传
    public List<Cheer_result> getCheerResult(){
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            return iuser.getResultList();
        }finally {
            session.close();
        }

    }

    //上传啦啦队员信息
    @Override
    public int updateCheerPlayerInfo(HashMap<String,String> map) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            int id = iuser.insertCheerPlayer(map);
            return id;
        }finally {
            session.close();
        }
    }

    //上传啦啦队信息
    @Override
    public int updateCheerInfo(HashMap<String, String> map) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            int id = iuser.insertCheerInfo(map);
            return id;
        }finally {
            session.close();
        }
    }
    //上传题目
    @Override
    public int updateQuestion(Map<String ,String> question) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            int id = iuser.updateQuestion(question);
            return id;
        }finally {
            session.close();
        }
    }
    //获取题目数量
    @Override
    public List<Question_num> getQuestionNum() {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            return iuser.getQuestionNum();

        }finally {
            session.close();
        }
    }

    //获取题目列表
    @Override
    public List<Question> getQuestionList(int kind) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            return iuser.getQuestionList(kind);

        }finally {
            session.close();
        }
    }
    //删除题目
    @Override
    public int delQuestionById(int questionId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            int i = iuser.delQuestionById(questionId);
            return i;
        }finally {
            session.close();
        }
    }
    //查询题目
    @Override
    public List<Question> getQuestionByKey(String key) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            return iuser.getQuestionByKey(key);
        }finally {
            session.close();
        }
    }
    //更新题目
    @Override
    public int updateQuestionById(Map<String,String> question) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IAdmin iuser = session.getMapper(IAdmin.class);
            return iuser.updateQuestionById(question);
        }finally {
            session.close();
        }
    }

//    public static void main(String[] args) {
//        AdminService adminService = new AdminServiceImp();
////        Question ques = new Question();
////        ques.setTitle("question");
////        ques.setA("A");
////        ques.setB("B");
////        ques.setC("C");
////        ques.setD("D");
////        ques.setAnswer("answer");
////        ques.setKind("kind");
////        ques.setQuestionId(2);
////        int i = adminService.updateQuestionById(ques);
////
////        System.out.println(i);
//        JsonUtil jsonUtil = new JsonUtil();
//
//        List <Question> questions = adminService.getQuestionByKey("你好");
//        String str = jsonUtil.listToJsonArray(questions);
//        System.out.println(str);
//    }


}
