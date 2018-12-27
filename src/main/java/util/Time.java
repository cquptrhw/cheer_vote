package util;

import java.sql.Timestamp;
import java.util.TimeZone;

public class Time {
    //设置过期时间
    public static long getTimeDiff() {
        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long todayEighteen=zero+20*60*60*1000-1;//今天19点59分59秒的毫秒数
        long tomorrowEighteen =todayEighteen + 24*60*60*1000-1 ; //明天19点59分59秒的毫秒数
        if(current>todayEighteen){
            Long different = tomorrowEighteen - current;
            return different;
        }else{
            Long different = todayEighteen -current;
            return different;
        }
    }
    //获取当天早上八点到晚上八点
    public static Timestamp[] getTimePeriod(){
        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long  morning= zero+8*60*60*1000-1;  //昨天20点00分01的毫秒数
        long evening=zero+20*60*60*1000-1;//今天19点59分59秒的毫秒数

        Timestamp [] timeList = new Timestamp[2];
        timeList[0]=new Timestamp(morning);
        timeList[1]=new Timestamp(evening);
        return timeList;
    }

    //获取今天凌晨12点到23:59:59
    public static Timestamp[] getTimePeriodList(){
        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long todayEight=zero;//今天8点59分59秒的毫秒数
        long todayTwen=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        Timestamp [] timeList = new Timestamp[2];
        timeList[0]=new Timestamp(todayEight);
        timeList[1]=new Timestamp(todayTwen);
        return timeList;
    }


    public static void main(String[] args) {
        Time time = new Time();
        Timestamp[] timestamps = time.getTimePeriodList();

            System.out.println(timestamps[0]);
            System.out.println(timestamps[1]);
    }


}
