package service;

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
}
