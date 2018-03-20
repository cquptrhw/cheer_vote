package util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public static void main(String[] args) throws NoSuchAlgorithmException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("classId",1);
        jsonObject.put("name","你好");
        String data = jsonObject.toString();
        long timeStamp = System.currentTimeMillis();
        int nonce = 8;
        System.out.println("base64 : " + encryptBASE64(data));
        System.out.println("MAD5 :" + md5(data + timeStamp + nonce));
        String str = sha1(md5(encryptBASE64(data) + timeStamp + nonce) + "cheer_vote");
        System.out.println("base64 : " +str );
        DataUtil.getData(timeStamp, String.valueOf(nonce),encryptBASE64(data),str);
    }
}