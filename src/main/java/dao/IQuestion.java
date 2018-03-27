package dao;


import dto.Qusetion_user;

import java.sql.Timestamp;
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
    //获取答题排行榜
    public List<LuckUser> getUserRank(Timestamp startTime, Timestamp endTime);
    //从Mysql获取今日答题数目
    public int getTodayNumFromMysql(String openId,Timestamp startTime, Timestamp endTime);
}
