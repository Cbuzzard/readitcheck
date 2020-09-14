package com.buzzardsview.readitcheck.model.dto;

public class QuestionCheckDto {

    private String answer;

    public QuestionCheckDto(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
