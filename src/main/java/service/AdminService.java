package service;

import dto.Cheer_result;
import dto.MyAdmin;
import dto.Question;
import dto.Question_num;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:34 2018/3/14
 */
public interface AdminService{
     //检验是否登录
     public boolean isLogin(HttpSession session);
     //检验是否为主管理员
     public int isMainAdmin(HttpSession session);
     //管理员登录
     public MyAdmin adminLogin(String username, String password);
     //获取拉拉队信息是否上传
     public List<Cheer_result> getCheerResult();
     //上传啦啦队伍队员信息
     public int updateCheerPlayerInfo(HashMap<String, String> map);
     //上传啦啦队文件
     public int updateCheerInfo(HashMap<String, String> map);
     //题目上传
     public int updateQuestion(Question question);
     //获取题目数量
     public List<Question_num> getQuestionNum();
     //获取题目列表
     public List<Question> getQuestionList(int kind);
     //删除题目
     public int delQuestionById(int questionId);
     //查询题目
     public  List<Question> getQuestionByKey(String key);
     //更新题目
     public  int updateQuestionById(Question question);


}

