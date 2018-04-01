package service;

import dto.user_assistance;

import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 14:34 2018/3/25
 */
public interface AssistanceService {
    //回答正确，进行加分
    public Boolean addUserAssistance(String openId,int assistance);
    //获取用户的助力数
    public int  getUserAssistance(String openId);
    //获取助力历史
    public String getAssistanceHistory(String openId);
    //为拉拉队增加助力数
    public Boolean updateCheerAssistance(List<user_assistance> user_assistanceList);
//    //判断助力数是否够
//    public boolean isEnough(String openId,int acount);
    //获取啦啦队的助力数
    public String getCheerDistance(List<Integer> list);
    //获取战队排行
    public String getGroupRank();
    //获取战队成员
    String getCheerNameByGroupId(String groupId);
}
