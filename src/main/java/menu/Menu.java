package menu;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import util.AccessToken;
import util.Const;
import util.CurlUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Menu {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Menu menu = new Menu();
        Button button1 = new Button();
        button1.setType("view");
        button1.setName("啦啦队投票");
        button1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Const.AppId+"&redirect_uri="+Const.redirect_uri+"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect");
        menu.addButton(button1);
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

