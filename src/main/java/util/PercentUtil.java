package util;

import java.text.NumberFormat;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 12:47 2018/4/1
 */
public class PercentUtil {
    public static String getPercent(double num1, double num2) {
        System.out.println("y");
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        //设置精确到个位
        numberFormat.setMaximumFractionDigits(0);
        float percnet = (float) num1 / (float) num2 ;
        String result = numberFormat.format((1-percnet)*100);
        String str =result+"%";
        System.out.println(str);
        return str;
    }
}
