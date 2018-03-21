package util;

import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 21:57 2018/3/20
 */
public class EncryptUtil {
    //md5加密
    public static String md5(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    //sha1加密
    public static String sha1(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }


    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) {
        byte[] bt;
        try {
            bt = (new BASE64Decoder()).decodeBuffer(key);
            return new String(bt,"utf-8");//如果出现乱码可以改成： String(bt, "utf-8")或 gbk
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) {
        byte[] bt = key.getBytes();
        return (new BASE64Encoder()).encodeBuffer(bt);
    }

    //加密测试
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //数据封装进json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("classId",1);
        jsonObject.put("content","你好");
        //将json转为string
        String data = jsonObject.toString();
        //对string进行base64编码
        String string1 = encryptBASE64(data);
        //获取unix时间戳
        String timeStamp = String.valueOf((System.currentTimeMillis()/1000));
        //随机获取的字符串
        String nonce = "a";
        //获取到的signature
        String  signature = EncryptUtil.sha1(EncryptUtil.md5(string1+timeStamp+nonce)+"cheer_vote");
//        System.out.println("signature : " +str );
//        DataUtil.getData(timeStamp,nonce,string1,str);
    }
}