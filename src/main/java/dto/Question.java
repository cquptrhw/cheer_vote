package dto;

import com.alibaba.fastjson.annotation.JSONField;

public class Question {
    @JSONField(name = "A")
    private String A;
    @JSONField(name = "B")
    private String B;
    @JSONField(name = "C")
    private String C;
    @JSONField(name = "D")
    private String D;
    private String answer;
    private String kind;
    private int questionId;
    private String title;

    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

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

    public void setKind(String kind) {
        this.kind = kind;
    }
    public String getKind() {
        return kind;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    public int getQuestionId() {
        return questionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
