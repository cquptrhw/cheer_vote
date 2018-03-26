package dto;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:13 2018/3/27
 */
public class LuckUser {
    private String nickname;
    private String headImgUrl;
    private String rightNum;
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setRightNum(String rightNum) {
        this.rightNum = rightNum;
    }
    public String getRightNum() {
        return rightNum;
    }
}
