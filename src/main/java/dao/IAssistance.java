package dao;

import dto.Assistance_history;

import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 15:17 2018/3/25
 */
public interface IAssistance {
    //更改用户助力数
    public int addUserAssistance(String openId,int assistance);
    //获取用户的助力数
    Integer getUserAssistance(String openId);
    //往用户助力数表插入新数据
    int insertUserAssistance(String openId);
    //获取助力历史
    List<Assistance_history> getAssistanceHistory(String openId);
}
