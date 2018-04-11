package Imp;

import dao.IPageInfo;
import dto.Cheer_firstPage;
import dto.Cheer_info;
import dto.Cheer_playerInfo;
import dto.StartPage;
import org.apache.ibatis.session.SqlSession;
import service.PageInfoService;
import util.JsonUtil;
import util.PercentUtil;
import util.SqlSessionFactoryUtil;
import util.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 20:55 2018/3/30
 */
public class PageInfoServiceImp implements PageInfoService {
    static SqlSessionFactoryUtil sqlSessionFactoryUtil;
    //获取拉拉队首页信息
    @Override
    public String getCheerFirstPage() {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        List<Cheer_firstPage>  cheer_firstPageList= iPageInfo.getCheerFirstPage();
        String str = JsonUtil.toJSONString(cheer_firstPageList);
        return str;
    }
    //获取啦啦队详情页信息
    @Override
    public String getCheerInfo(String classId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        List<Cheer_info> cheer_infoList = iPageInfo.getCheerInfo(classId);
        String str = JsonUtil.toJSONString(cheer_infoList);
        return str;
    }
    //获取启动页信息
    @Override
    public String getStartPage(String openId) {
        //获取助力数排行部分
        Map<String,String> assistanceMap = getAssistanceRankInfo(openId);
        Map<String,String> rightNumMap =getRightAnswerNumRankInfo(openId);
        Map<String,String> todayNumMap =getTodayNumRankInfo(openId);
        Map<String,String> startPage = new HashMap<>();
        startPage.putAll(assistanceMap);
        startPage.putAll(rightNumMap);
        startPage.putAll(todayNumMap);
        String str = JsonUtil.toJSONString(startPage);
        System.out.println(str);
        return str;
    }
    //获取启动页助力信息
    @Override
    public Map<String,String> getAssistanceRankInfo(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        //获取查询的时间段
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        //获取查询结果
        Map<String,Integer> res=iPageInfo.getAssistanceRankInfo(openId,timeArray[0],timeArray[1]);
//        System.out.println(JsonUtil.toJSONString(res));
        //初始化返回值的map
        Map<String,String> startPage =getPercentUtil(res,"assistance");
        return startPage;
    }
    //获取启动页正确答对的排行
    @Override
    public Map<String, String> getRightAnswerNumRankInfo(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        //获取查询的时间段
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        //获取查询结果
        Map<String,Integer> res=iPageInfo.getRightAnswerNumRankInfo(openId,timeArray[0],timeArray[1]);
//        System.out.println(JsonUtil.toJSONString(res));
        //获取所占百分比
        Map<String,String> startPage = getPercentUtil(res,"rightNum");
        return startPage;
    }

    @Override
    public Map<String, String> getTodayNumRankInfo(String openId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        //获取查询的时间段
        final Time time = new Time();
        Timestamp[]timeArray = time.getTimePeriod();
        //获取查询结果
        Map<String,Integer> res=iPageInfo.getTodayNumRankInfo(openId,timeArray[0],timeArray[1]);
//        System.out.println(JsonUtil.toJSONString(res));
        //获取所占百分比
        Map<String,String> startPage = getPercentUtil(res,"todayNum");
        return startPage;
    }

    //获取所超过的百分比
    @Override
    public Map<String, String> getPercentUtil(Map<String, Integer> res, String parameterName) {
        int assistance =0;
        int rank =0;
        int total =0;
        //判断传入的数据是否为空
        if(!( res == null || res.isEmpty())){
            if(!(res.get("num") == null)){
                assistance = res.get("num");
            }
            //获取排名超过的百分;
            if(!(res.get("rank") == null)){
                rank = Integer.valueOf(res.get("rank")).intValue();
            }
            if(!(res.get("total") ==null))
                total =Integer.valueOf(res.get("total")).intValue();
        }
        Map<String,String> startPage = new HashMap();
        //获取所超过的百分比
        String assistanceRank = PercentUtil.getPercent(rank,total);
        //将信息写入Map返回
        startPage.put(parameterName, String.valueOf(assistance));
        startPage.put(parameterName+"Rank", assistanceRank);
//        System.out.println(startPage.toString());
        return startPage;
    }
    //获取啦啦队员信息
    @Override
    public String getCheerPlayerInfo(String classId) {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        List<Cheer_playerInfo> cheer_playerInfos = iPageInfo.getCheerPlayerInfo(classId);
        String str = JsonUtil.toJSONString(cheer_playerInfos);
        return str;
    }


    public static void main(String[] args){
        PageInfoService pageInfoService = new PageInfoServiceImp();
//        List<Integer> list = new ArrayList<Integer>();
//        list.add(7);
//        list.add(3);
//        list.add(1);
//        System.out.println(JsonUtil.toJSONString(list));
//        pageInfoService.getAssistanceRankInfo("bb");
//        pageInfoService.getRightAnswerNumRankInfo("kk");
//        pageInfoService.getStartPage("bb");
        System.out.println(pageInfoService.getCheerPlayerInfo("9"));

//        System.out.println(str);
    }


}
