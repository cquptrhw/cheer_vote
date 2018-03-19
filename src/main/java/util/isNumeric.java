package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//判断输入信息是否为数字
public class isNumeric {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
