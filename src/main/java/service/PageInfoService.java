package service;

import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 20:46 2018/3/30
 */
public interface PageInfoService {
    //获取啦啦队首页信息
    public String getCheerFirstPage();
    //获取拉拉队详情页信息
    public String getCheerInfo(String classId);
    //获取启动页信息
    public String getStartPage(String openId);
    //获取启动页助力信息
    public Map<String,String> getAssistanceRankInfo(String openId);
    //获取启动页答对题目信息
    public Map<String,String> getRightAnswerNumRankInfo(String openId);
    //获取今日答题数目信息
    public Map<String,String> getTodayNumRankInfo(String openId);
    //获取所超过的百分比
    public Map<String,String> getPercentUtil(Map<String,Integer> res,String parameterName);
    //获取啦啦队员信息
    public String getCheerPlayerInfo(String classId);
}
