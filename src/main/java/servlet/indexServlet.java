package servlet;

import message.Textmessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import util.Const;
import util.EncryptUtil;
import util.StringUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 20:01 2018/4/8
 */
public class indexServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            request.getInputStream(), "UTF-8"
                    )
            );
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String encryptMsg = builder.toString();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            Document document = builder1.parse(
                    new InputSource(
                            new StringReader(encryptMsg)
                    )
            );
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();
            Map<String, String> result = new HashMap<String, String>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (!node.getNodeName().equals("#text")) {
                    String nName = node.getNodeName();
                    String nValue = node.getTextContent();
                    result.put(nName, nValue);
                }
            }
            String encrypt_type =request.getParameter("encrypt_type");

                String toUserName = result.get("FromUserName");
                String Content = result.get("Content");
                String MsgType = result.get("MsgType");
                String Event = result.get("Event");

                String EventKey = result.get("EventKey");
                System.out.println(result);
                String hostid = toUserName;
                if("text".equals(MsgType)){
                    String content = "小王八蛋";
                    String res = Textmessage.subscribemessage(result,toUserName,content);
                    response.setContentType("text/JavaScript; charset=utf-8");
                    response.getWriter().println(res);

                }else if("subscribe".equals(Event)){
                    String content = "Welcome to Newtol\n"+"回复【游戏规则】查看谁是卧底游戏规则\n"+"回复【惩罚】查看游戏惩罚\n"+"回复【redrock】查看红岩网校";
                    String res = Textmessage.subscribemessage(result,toUserName,content);
                    response.setContentType("text/JavaScript; charset=utf-8");
                    response.getWriter().println(res);
                }else if ("V1001_TODAY_MUSIC".equals(EventKey)){
                    String content =  "正在创建谁是卧底房间,请输入游戏人数(4-20之间，不包括法官哦)，或点击下方链接创建房间 \n" +
                            "<a href=\"http://enzvb2.natappfree.cc/form?hostId=" + toUserName+"\">点击这里创建房间</a>";
                    String res = Textmessage.subscribemessage(result,toUserName,content);
                    response.setContentType("text/JavaScript; charset=utf-8");
                    response.getWriter().println(res);

                } else if ("V1001_TODAY_GAME1".equals(EventKey)){
                    String content = "快点击连接参与游戏吧<a href=\"http://enzvb2.natappfree.cc/notouch\">点击这里</a>";
                    String res = Textmessage.subscribemessage(result,toUserName,content);
                    response.setContentType("text/JavaScript; charset=utf-8");
                    response.getWriter().println(res);
                }else if ("V1001_TODAY_GAME2".equals(EventKey)){
                    String content = "快点击连接参与游戏吧<a href=\"http://enzvb2.natappfree.cc/2048\">点击这里</a>";
                    String res = Textmessage.subscribemessage(result,toUserName,content);
                    response.setContentType("text/JavaScript; charset=utf-8");
                    response.getWriter().println(res);
                }else if("V1001_TODAY_image".equals(EventKey)){
                    String res = Textmessage.redrockmessage(result,toUserName);
                    response.setContentType("image/jpg; charset=utf-8");
                    System.out.println(res);
                    response.getWriter().print(res);
                }
            } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //微信公众号管理界面配置参数
        String token = Const.Token;
        //获取请求的四个参数
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        System.out.println(signature);
        System.out.println("y");
        //检验四个参数是否有效
        if (!StringUtil.hasBlank(signature, timestamp, nonce, echostr)) {
            String[] list = {token, timestamp, nonce};
            //字典排序
            Arrays.sort(list);
            //拼接字符串
            StringBuilder builder = new StringBuilder();
            for (String str : list) {
                builder.append(str);
            }
            //sha1加密
            String hashcode = EncryptUtil.sha1(builder.toString());
            //不区分大小写差异情况下比较是否相同
            if (hashcode.equalsIgnoreCase(signature)) {
                //响应输出
                resp.getWriter().println(echostr);
            }
        }
    }
}
