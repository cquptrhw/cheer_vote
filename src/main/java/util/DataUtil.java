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
    public static Map getData(long timestamp, String nonce, String string, String singnature) throws NoSuchAlgorithmException {
        Map map = new HashMap<String, String>();
        //检验时间的有效性
        if(isTime(timestamp)){
            //检验数据的有效性
            String str = EncryptUtil.sha1(EncryptUtil.md5(string+timestamp+nonce)+"cheer_vote") ;
            if(str.equals(singnature)){
                //解释string中的数据
                String json = EncryptUtil.decryptBASE64(string);
                map= JsonUtil.stringToCollect(json);
            }else {
                System.out.println("FASLE");
            }
        }else {
            System.out.println("数据过期");
        }
        return map;
    }



    //检验数据是否过期
    public static boolean isTime(long timestamp){
        long current = System.currentTimeMillis() ;
        long  different = current - timestamp;
        System.out.println(different);
        if (different<=7200){
            return true;
        }else {
            return false;
        }
    }
}