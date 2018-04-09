package Imp;

import dao.IPageInfo;
import dto.Cheer_firstPage;
import dto.Cheer_info;
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
    public String getCheerInfo() {
        SqlSession session = sqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        IPageInfo iPageInfo = session.getMapper(IPageInfo.class);
        List<Cheer_info> cheer_infoList = iPageInfo.getCheerInfo();
        String str = JsonUtil.toJSONString(cheer_infoList);
        return str;
    }
    //获取启动页信息
    @Override
    public String getStartPage(String openId) {
        Map<String,String> assistanceMap = getAssistanceRankInfo(openId);
        StartPage startPage = new StartPage();
        startPage.setAssistance(assistanceMap.get("assistance"));
        startPage.setAssistanceRank(assistanceMap.get("assistanceRank"));
        return null;
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
        System.out.println(JsonUtil.toJSONString(res));
        //初始化返回值的map
        Map<String,String> startPage = new HashMap<String, String>();
        int assistance =0;
        int rank =0;
        int total =0;
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
        System.out.println(assistance);
        startPage.put("assistance", String.valueOf(assistance));
        String assistanceRank = PercentUtil.getPercent(rank,total);
        startPage.put("assistanceRank", assistanceRank);
        System.out.println(startPage.toString());
        return null;
    }

    public static void main(String[] args){
        PageInfoService pageInfoService = new PageInfoServiceImp();
//        List<Integer> list = new ArrayList<Integer>();
//        list.add(7);
//        list.add(3);
//        list.add(1);
//        System.out.println(JsonUtil.toJSONString(list));
        pageInfoService.getAssistanceRankInfo("ccc");

//        System.out.println(str);
    }


}
