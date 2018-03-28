package dto;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:33 2018/3/28
 */
public class Cheer_assistance {
    private String groupId;
    private String groupName;
    private int  distance;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getGroupId() {

        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
