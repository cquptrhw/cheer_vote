package dao;

import dto.Cheer_firstPage;
import dto.Cheer_info;
import dto.Cheer_playerInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 20:49 2018/3/30
 */
public interface IPageInfo {
    //啦啦队首页信息获取
    List<Cheer_firstPage> getCheerFirstPage();
    //获取拉拉队信息
    List<Cheer_info>  getCheerInfo(String classId);
    //获取启动页助力信息
    Map<String, Integer> getAssistanceRankInfo(String openId, Timestamp startTime, Timestamp endTime);
    //获取启动页正确答题数信息
    Map<String,Integer> getRightAnswerNumRankInfo(String openId, Timestamp startTime, Timestamp endTime);
    //获取启动页今日答题数信息
    Map<String,Integer> getTodayNumRankInfo(String openId, Timestamp startTime, Timestamp endTime);
    //获取啦啦队员信息
    List<Cheer_playerInfo> getCheerPlayerInfo(String classId);
}
