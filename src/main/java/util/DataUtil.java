package util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 22:56 2018/3/20
 */
public class DataUtil {
    //解析数据
    public static Map<String,String> getData(String timestamp, String nonce, String string, String singnature) throws NoSuchAlgorithmException {
        Map map = new HashMap<String, String>();


        //检验时间的有效性
        if(isTime(Long.parseLong(timestamp))){
            //检验数据的有效性
            String str = EncryptUtil.sha1(EncryptUtil.md5(string+timestamp+nonce)+"cheer_vote") ;
            System.out.println(str);
            if(singnature.equals(str)){
                //解释string中的数据
                String json = EncryptUtil.decryptBASE64(string);
                map= JsonUtil.stringToCollect(json);
                return map;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }



    //检验数据是否过期
    public static boolean isTime(long timestamp){
        long current = System.currentTimeMillis()/1000 ;
        long  different = current - timestamp;
        System.out.println(different);
        if (different<=(5*3600)){
            return true;
        }else {
            return false;
        }
    }
}