package util;

import java.text.NumberFormat;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 12:47 2018/4/1
 */
public class PercentUtil {
    public static String getPercent(double num1, double num2) {
        if (num2 <= 100){
            num2=100;
        }
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到个位
        numberFormat.setMaximumFractionDigits(0);
        float percnet = (float) num1 / (float) num2 ;
        String result = numberFormat.format((1-percnet)*100);
        if(result.equals("100")){
            result="0";
        }
        String str =result+"%";
        System.out.println(str);
        return str;
    }
}
