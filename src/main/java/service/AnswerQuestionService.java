package service;

import dto.Qusetion_user;

import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:24 2018/3/22
 */
public interface AnswerQuestionService {
    //判断今天是否答过题
    public boolean isNewAnswer(String openId);
    //从Redis获取题目
    String getQuestionFromRedis(String openId, int todayNum);
    //从Mysql获取题目
    public boolean getQusertionFromMysql(String openId);
}
