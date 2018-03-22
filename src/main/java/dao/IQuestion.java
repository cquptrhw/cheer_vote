package dao;


import dto.Qusetion_user;

import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:47 2018/3/23
 */
public interface IQuestion {
    //从mysql获取题目
    public List<Qusetion_user> getQuestionFromMysql(int kind);
}
