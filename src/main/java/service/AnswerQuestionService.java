package service;

import dto.Qusetion_user;

import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:24 2018/3/22
 */
public interface AnswerQuestionService {
    //从redis获取今天答题数目
    public int getTodayNum(String openId);
    //从mysql获取今日答题数目
    public int getTodayNumFromMysql(String openId);
    //从Redis获取题目
    String getQuestionFromRedis(String openId, int todayNum);
    //从Mysql获取题目
    public boolean getQusertionFromMysql(String openId);
    //从redis获取答案
    public String getAnswerFromRedis(String questionId);
    //从mysql获取答题
    public String getAnswerFromMysql(String questionId);
    //将答题插入数据库
    public boolean addAnswerHistory(Map map);
    //判断用户是否答过此题
    public boolean isAnswer(Map map);
    //获取用户答题榜单
    public String getUserRank();

}
