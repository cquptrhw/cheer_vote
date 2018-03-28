package dto;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 19:26 2018/3/28
 */
public class user_assistance {

        private String openId;
        private String classId;
        private String num;
        private int groupId;
        public void setClassId(String classId) {
            this.classId = classId;
        }
        public String getClassId() {
            return classId;
        }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setNum(String num) {
            this.num = num;
        }
        public String getNum() {
            return num;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
        public int getGroupId() {
            return groupId;
        }

}
