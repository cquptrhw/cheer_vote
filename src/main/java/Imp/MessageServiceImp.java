package Imp;

import dao.IMessage;
import dto.Message_user;
import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;
import service.MessageService;
import util.JedisUtil;
import util.JsonUtil;
import util.SqlSessionFactoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:20 2018/3/21
 */
public class MessageServiceImp implements MessageService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //上传留言
    @Override
    public Map<String,String> updateMessage(Map map) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IMessage iMessage=session.getMapper(IMessage.class);
            int id = iMessage.updateMessage(map);
            if (id != 0){
                return map;
            }
        } finally {
            session.close();
        }
        return map;
    }
    //判断用户是否已经登录
    @Override
    public String isLogin(String sessionId) {
        String openId = JedisUtil.getString(sessionId);
        if(openId == null || openId.isEmpty()){
            return null;
        }else {
            return openId;
        }
    }
    //用户点赞
    @Override
    public String praiseMessage(String openId, String contentId) {
        String str = null;
        //判断是否点过赞
        boolean i = isPraise(openId,contentId);
        if(i){
            str = "已经点赞过了哦";
        }else{
            SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
            try {
                IMessage iMessage=session.getMapper(IMessage.class);
                int id = iMessage.praiseMessage(openId,contentId);
                //判断是否成功插入数据库
                if (id != 0){
                    //是否插入redis
                    Long rs = insertOpenIdToRedis(openId,contentId);
                    if(rs == 1){
                        str = "点赞成功";
                    }else {
                        str = "点赞失败：插入redis失败";
                    }
                }else {
                    str = "点赞失败:插入数据库失败";
                }
            } finally {
                session.close();
            }
        }
        //获取点赞数
        long num = getPraiseById(contentId);
        //封装点赞后的结果
        HashMap<String,String> map= new HashMap<String, String>();
        map.put("message",str);
        map.put("contentId",contentId);
        map.put("praiseNum", String.valueOf(num));
        String res =JsonUtil.toJSONString(map);
        return res;

    }
    //判断用户是否已经点过赞了
    @Override
    public boolean isPraise(String openId ,String contentId) {
        String key="message :"+contentId;
        boolean i =JedisUtil.getJedis().sismember(key,openId);
        return i;
    }
    //将用户插入点赞列表
    @Override
    public Long insertOpenIdToRedis(String openId, String contentId){
        String key="message :"+contentId;
        Long i =JedisUtil.getJedis().sadd(key,openId);
        return i;
    }
    //获取文章的点赞数
    @Override
    public Long getPraiseById(String contentId) {
        String key="message :"+contentId;
//        System.out.println(key);
        Jedis jedis= JedisUtil.getJedis();
        long  i=jedis.scard(key);
//        System.out.println(i);
        return i;
    }
    //获取留言
    @Override
    public String getMessageList(String classId, int page) {
        String str = null;
        int pageSize = 10;
        int[] array = getPage(page,pageSize);
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        try {
            IMessage iMessage=session.getMapper(IMessage.class);
            List<Message_user> list = iMessage.getMessageList(classId,array[0],array[1]);
            //获取每篇文章的点赞数
            for(Message_user message_user:list)
            {
                long contentId = message_user.getContentId();
                Long praiseNum = getPraiseById(String.valueOf(contentId));
                message_user.setPrasieNum(praiseNum);
            }
            str = JsonUtil.listToJsonArray(list);
        } finally {
            session.close();
        }
        return str;
    }

    @Override
    public int[] getPage(int page, int pageSize) {
        if(page == 0){
            page = 1;
        }
        if(pageSize == 0){
            pageSize = 10;
        }
        int pageStart = (page-1)* pageSize;
        int pageEnd = page * pageSize;
        int[]  arrays = new int[2];
        arrays[0]=pageStart;
        arrays[1]=pageEnd;
        return arrays;
    }


    public static void main(String[] args){
        MessageService messageService = new MessageServiceImp();
//        HashMap m1 = new HashMap();
//        m1.put("openId", "8");
//        m1.put("classId", "31");
//        m1.put("content", "你好");
        String str  = messageService.getMessageList("1",1);
        System.out.println(str);
    }
}
