package util;
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
//    //sha1加密
//    public static String sha1(String data) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA1");
//        md.update(data.getBytes());
//        StringBuffer buf = new StringBuffer();
//        byte[] bits = md.digest();
//        for (int i = 0; i < bits.length; i++) {
//            int a = bits[i];
//            if (a < 0) a += 256;
//            if (a < 16) buf.append("0");
//            buf.append(Integer.toHexString(a));
//        }
//        return buf.toString();
//    }


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

    private static final char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 此类不需要实例化
     */
    private EncryptUtil() {
    }

    /**
     * 32位MD5加密，结果大写
     * @param str
     * @return
     */
    public static String MD5(String str) {
        return encode(str, "MD5");
    }

    /**
     * 32位SHA1加密，结果大写
     * @param str
     * @return
     */
    public static String sha1(String str) {
        return encode(str, "SHA-1");
    }

    private static String encode(String str, String algorithm) {
        String rs = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(str.toString().getBytes("UTF-8"));
            rs = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("该加密方式不存在");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 将byte数组变为16进制对应的字符串
     * @param byteArray byte数组
     * @return 转换结果
     */
    private static String byteToStr(byte[] byteArray) {
        int len = byteArray.length;
        StringBuilder strDigest = new StringBuilder(len * 2);
        for (byte aByteArray : byteArray) {
            strDigest.append(byteToHexStr(aByteArray));
        }
        return strDigest.toString();
    }

    private static String byteToHexStr(byte mByte) {
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }
//    //加密测试
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //数据封装进json
//        com.alibaba.fastjson.JSONObject jsonObject =new com.alibaba.fastjson.JSONObject();
//        jsonObject.put("classId",2);
//        jsonObject.put("groupId",3);
//        jsonObject.put("num",5);
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(jsonObject);
        com.alibaba.fastjson.JSONObject jsonObject1 =new com.alibaba.fastjson.JSONObject();
        jsonObject1.put("classId",2);
        jsonObject1.put("groupId",7);
        jsonObject1.put("num",5);
        //jsonArray.add(jsonObject1);

        //将json转为string
        //String data = jsonArray.toString();
        String data = jsonObject1.toString();
        System.out.println(data);
        //对string进行base64编码
        String string1 = encryptBASE64(data);
        System.out.println(string1);
//        //获取unix时间戳
//        String timeStamp = String.valueOf((System.currentTimeMillis()/1000));
//        //随机获取的字符串
//        String nonce = "a";
//        //获取到的signature
//        String  signature = EncryptUtil.sha1(EncryptUtil.md5(string1+timeStamp+nonce)+"cheer_vote");
//        System.out.println("signature : " +str );
        //boolean res =DataUtil.getData("1523587709","a","eyJxdWVzdGlvbklkIjoyMDYsImFuc3dlciI6IkEifQ==","25b639e0115eb8f6334a6858908ce1543ef92a13");
//        System.out.println(JsonUtil.toJSONString(map));

    }
}