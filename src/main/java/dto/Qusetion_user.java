package dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author: REN
 * @Description:
 * @Date: Created in 0:20 2018/3/23
 */
public class Qusetion_user {
    @JSONField(name = "A")
    private String A;
    @JSONField(name = "B")
    private String B;
    @JSONField(name = "C")
    private String C;
    @JSONField(name = "D")
    private String D;
    private String answer;
    private int questionId;
    private int todayNum;
    private String title;

    public void setA(String A) {
        this.A = A;
    }
    public String getA() {
        return A;
    }

    public void setB(String B) {
        this.B = B;
    }
    public String getB() {
        return B;
    }

    public void setC(String C) {
        this.C = C;
    }
    public String getC() {
        return C;
    }

    public void setD(String D) {
        this.D = D;
    }
    public String getD() {
        return D;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAnswer() {
        return answer;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public int getQuestionId() {
        return questionId;
    }

    public void setTodayNum(int todayNum) {
        this.todayNum = todayNum;
    }
    public int getTodayNum() {
        return todayNum;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

}
