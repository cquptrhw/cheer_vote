package dao;

import dto.Cheer_firstPage;
import dto.Cheer_info;

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
    List<Cheer_info>  getCheerInfo();
    //获取助力信息
    Map<String, String> getAssistanceRankInfo(String openId, Timestamp startTime, Timestamp endTime);
}
