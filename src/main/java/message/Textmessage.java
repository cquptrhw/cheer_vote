package message;

import java.util.Map;


//消息回复
public class Textmessage {
    public static String redrockmessage(Map<String, String> result, String toUserName) {
        String xml = "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[%s]]></MsgType>" +
                "<ArticleCount>%s</ArticleCount>" +
                "<Articles>" +
                "<item>" +
                "<Title><![CDATA[%s]]></Title> " +
                "<Description><![CDATA[%s]]></Description>" +
                "<PicUrl><![CDATA[%s]]></PicUrl>" +
                "<Url><![CDATA[%s]]></Url>" +
                "</item>" +
                "</Articles>" +
                "</xml>";
        String articlecount = "1";
        String fromUser = result.get("ToUserName");
        String createTime = System.currentTimeMillis() / 1000 + "";
        String msgType = "news";
        String title = "redrock";
        String description = "这是重庆邮电大学最牛逼的学生组织没有之一";
        String picurl = "http://hongyan.cqupt.edu.cn/images/index_top.jpg";
        String url = "http://hongyan.cqupt.edu.cn/";
        String res = String.format(xml, toUserName, fromUser, createTime, msgType, articlecount, title, description, picurl, url);
        return res;
    }
    public static String subscribemessage(Map<String, String> result, String toUserName,String content) {
        String xml = "<xml>" +
                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                "<CreateTime>%s</CreateTime>" +
                "<MsgType><![CDATA[%s]]></MsgType>" +
                "<Content><![CDATA[%s]]></Content>" +
                "</xml>";
        String fromUser = result.get("ToUserName");
        String createTime = System.currentTimeMillis() / 1000 + "";
        String msgType = "text";
        String res = String.format(xml, toUserName, fromUser, createTime, msgType, content);
        return res;
    }
}

