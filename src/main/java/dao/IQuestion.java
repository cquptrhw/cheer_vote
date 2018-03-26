package dao;


import dto.Qusetion_user;

import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:47 2018/3/23
 */
public interface IQuestion {
    //从mysql获取题目
    public List<Qusetion_user> getQuestionFromMysql(int kind);
    //从Mysql题目答案
    public String getAnswerFromMysql(String questionId);
    //插入答题历史
    public int insertAnswerHistory(Map map);


    }
