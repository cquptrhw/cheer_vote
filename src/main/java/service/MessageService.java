package service;

import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 22:51 2018/3/20
 */
public interface MessageService {
    //上传留言
    public Map<String,String> updateMessage(Map map);
    //判断该用户是否已经登录
    public String isLogin(String sessionId);
    //用户点赞
    public String praiseMessage(String openId,String contentId);
    //判断用户是否已经点过赞
    public boolean isPraise(String openId,String contentId);
    //将点赞用户插入redis
    public  Long insertOpenIdToRedis(String qqqq, String s);
    //获取文章的点赞数
    public Long getPraiseById(String contentId);
    //获取留言列表
    public String getMessageList(String classId ,Integer page);
    //获取分页
    public int[] getPage(Integer page, int pageSize);
}
