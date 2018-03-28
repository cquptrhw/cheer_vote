package util;

import dto.user_assistance;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 22:56 2018/3/20
 */
public class DataUtil {
    //验证数据是否有效
    public static boolean getData(String timestamp, String nonce, String string, String singnature) throws NoSuchAlgorithmException {
        //检验时间的有效性
        if(isTime(Long.parseLong(timestamp))){
            //检验数据的有效性
            String str = EncryptUtil.sha1(EncryptUtil.md5(string+timestamp+nonce)+"cheer_vote") ;
            System.out.println(str);
            if(singnature.equals(str)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    //检验数据是否过期
    public static boolean isTime(long timestamp){
        long current = System.currentTimeMillis()/1000 ;
        long  different = current - timestamp;
//        System.out.println(different);
        if (different<=(24*7*5*3600)){
            return true;
        }else {
            return false;
        }
    }
}