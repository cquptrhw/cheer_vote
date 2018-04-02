package dao;

import dto.Cheer_result;
import dto.MyAdmin;
import dto.Question;
import dto.Question_num;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAdmin {
    //获取啦啦队信息上传情况
    public List<Cheer_result> getResultList();
    //管理员登录
    public MyAdmin getUser(String username, String password);
    //上传啦啦队员信息
    public int insertCheerPlayer(HashMap<String, String> map);
    //上传啦啦队信息
    public int insertCheerInfo(HashMap<String, String> map);
    //上传题目
    int updateQuestion(Map<String,String> question);
    //获取题目数量
    List<Question_num> getQuestionNum();
    //获取题目列表
    List<Question> getQuestionList(int kind);
    //删除题目
    int delQuestionById(int questionId);
    //查询题目
    List<Question> getQuestionByKey(String key);
    //更新题目
    int updateQuestionById(Map<String,String> question);

//    public List<Admin> getUserList();
}
