package com.buzzardsview.readitcheck.model.dto.question;

public class QuestionCheckDto {

    private int id;
    private String answer;

    public QuestionCheckDto(int id, String answer) {
        this.id = id;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
