package dto;

import java.security.Timestamp;
import java.sql.Date;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:04 2018/3/22
 */
public class Message_user {


    public long prasieNum;
    public String created_time;
    public long contentId;
    public String content;
    public String nickName;
    public String headImgUrl;

    public long getPrasieNum() {
        return prasieNum;
    }

    public void setPrasieNum(long prasieNum) {
        this.prasieNum = prasieNum;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }


    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public void setContent(String content) {
            this.content = content;
        }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
            return content;
        }


}
