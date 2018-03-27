package Imp;

import dao.IAssistance;
import dto.Assistance_history;
import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AssistanceService;
import servlet.answer.UserGetAnswerServlet;
import util.Const;
import util.JedisUtil;
import util.JsonUtil;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 14:35 2018/3/25
 */
public class AssistanceServiceImp implements AssistanceService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    protected static Logger logger = LoggerFactory.getLogger(UserGetAnswerServlet.class);
    //修改助力数
    @Override
    public Boolean addUserAssistance(String openId, int assistance) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        int num = assistance+1;
        //更改mysql数据
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        int i = iAssistance.addUserAssistance(openId,num);
        //更改redis数据
        JedisUtil.getJedis().hset(Const.Assistance,openId, String.valueOf(num));
        session.close();
        if(i!=0){
            return false;
        }else {
            return true;
        }

    }
    //获取用户的助力数
    @Override
    public int getUserAssistance(String openId) {
        String num = JedisUtil.getJedis().hget(Const.Assistance,openId);
        int assistance = 0;
        //判断redis中是否有助力数
        if(num == null||num.isEmpty()){
            SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
            //从mysql获取用户的助力数,若为零就执行插入
            IAssistance iAssistance = session.getMapper(IAssistance.class);
            //判断Mysql中是否存在
            Integer k = iAssistance.getUserAssistance(openId);
            if(k==null){
                //不存在就在mysql中插入空数据
                int i = iAssistance.insertUserAssistance(openId);
                if(i!=1) {
                    logger.error("错误原因 :插入失败");
                }else {
                    assistance=0;

                }
            }else{
                assistance = k;
            }
            session.close();
        }else{
            assistance = Integer.parseInt(num);
        }
        //插入redis进行缓存
        JedisUtil.getJedis().hset(Const.Assistance,openId, String.valueOf(assistance));

        return assistance;
    }
    //获取用户助力历史
    @Override
    public String getAssistanceHistory(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IAssistance iAssistance = session.getMapper(IAssistance.class);
        List<Assistance_history> assistance_historyList=iAssistance.getAssistanceHistory(openId);
        String str = JsonUtil.toJSONString(assistance_historyList);
        System.out.println(str);
        return str;
    }
       public static void main(String[] args){
       AssistanceService assistanceService = new AssistanceServiceImp();

//        HashMap m1 = new HashMap();
//        m1.put("openId", "8");
//        m1.put("classId", "31");
//        m1.put("content", "你好");
        String str = assistanceService.getAssistanceHistory("aaa");
    }


}
