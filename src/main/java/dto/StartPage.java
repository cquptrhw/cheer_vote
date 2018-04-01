package dto;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 16:03 2018/3/31
 */
public class StartPage {
   private String todayNum;
   private String todayNumRank;
   private String rightNum;
   private String rightNumRank;
   private String assistance;

    public String getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(String todayNum) {
        this.todayNum = todayNum;
    }

    public String getTodayNumRank() {
        return todayNumRank;
    }

    public void setTodayNumRank(String todayNumRank) {
        this.todayNumRank = todayNumRank;
    }

    public String getRightNum() {
        return rightNum;
    }

    public void setRightNum(String rightNum) {
        this.rightNum = rightNum;
    }

    public String getRightNumRank() {
        return rightNumRank;
    }

    public void setRightNumRank(String rightNumRank) {
        this.rightNumRank = rightNumRank;
    }

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = assistance;
    }

    public String getAssistanceRank() {
        return assistanceRank;
    }

    public void setAssistanceRank(String assistanceRank) {
        this.assistanceRank = assistanceRank;
    }

    private String assistanceRank;


}
