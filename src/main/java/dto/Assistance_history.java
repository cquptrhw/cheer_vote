package dto;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 23:26 2018/3/27
 */
public class Assistance_history {
        private String classId;
        private String className;
        private int distance;
        private int assistance;
        private int groupId;
        private String groupName;
        public void setClassId(String classId) {
            this.classId = classId;
        }
        public String getClassId() {
            return classId;
        }

        public void setClassName(String className) {
            this.className = className;
        }
        public String getClassName() {
            return className;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
        public int getDistance() {
            return distance;
        }

        public void setAssistance(int assistance) {
            this.assistance = assistance;
        }
        public int getAssistance() {
            return assistance;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }
        public int getGroupId() {
            return groupId;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }
        public String getGroupName() {
            return groupName;
        }


}
