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
    public String getCheerInfo();
    //获取启动页信息
    public String getStartPage(String openId);
    //获取启动页助力信息
    public Map<String,String> getAssistanceRankInfo(String openId);
}
