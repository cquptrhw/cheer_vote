package menu;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.AccessToken;
import util.CurlUtil;

import java.util.ArrayList;
import java.util.List;


public class Menu {

    public static void main(String[] args) {
        Menu menu = new Menu();

        Button button1 = new Button();
        button1.setType("click");
        button1.setName("谁是卧底");
        button1.setKey("V1001_TODAY_MUSIC");

        Button button2 = new Button();
        button2.setName("菜单");

        Button button6 = new Button();
        button6.setName("放松2下");


        Button button3 = new Button();
        button3.setType("click");
        button3.setName("关于我们");
        button3.setKey("V1001_TODAY_image");


        Button button4 = new Button();
        button4.setType("view");
        button4.setName("百度");
        button4.setUrl("https://www.baidu.com");

        Button button5 = new Button();
        button5.setType("click");
        button5.setName("别踩白块");
        button5.setKey("V1001_TODAY_GAME1");

        Button button7 = new Button();
        button7.setType("click");
        button7.setName("2048");
        button7.setKey("V1001_TODAY_GAME2");


        button2.addButton(button3);
        button2.addButton(button4);
        button6.addButton(button5);
        button6.addButton(button7);

        menu.addButton(button1);
        menu.addButton(button6);
        menu.addButton(button2);
        String result = updateMenu(menu);
        System.out.println(result);
    }


    public static String updateMenu(Menu menu) {

        String accessToken = AccessToken.getAccessToken();
//        System.out.println(accessToken);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        String menuData = menu.toJson();
        return CurlUtil.postData(url, menuData);
    }

    private List<Button> button;

    public String toJson() {
        JSONArray buttonData = new JSONArray();
        JSONObject menuData = new JSONObject();
        for (int i = 0; i < button.size(); i++) {
            Button b = button.get(i);
            buttonData.add(i, b.toJson());
        }
        menuData.put("button", buttonData);
        return menuData.toString();
    }

    public void addButton(Button button) {
        if (this.button == null) {
            this.button = new ArrayList<Button>();
        }
        this.button.add(button);
    }
}

