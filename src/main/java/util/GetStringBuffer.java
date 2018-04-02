package util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:00 2018/4/1
 */
public class GetStringBuffer {
    public static String getString(HttpServletRequest req){
        java.lang.StringBuffer json = new java.lang.StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return String.valueOf(json);
    }
}
