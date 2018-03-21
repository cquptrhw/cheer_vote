package dao;

import dto.Message_user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:23 2018/3/21
 */
public interface IMessage {
    //上传留言
    public int updateMessage(Map map);
    //留言点赞
    public int praiseMessage(String openId,String contentId);
    //获取留言
    public List<Message_user> getMessageList (String classId , int pageStart, int pageEnd);
}
