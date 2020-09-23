package com.buzzardsview.readitcheck.model.dto.question;

public class QuestionGetDto {

    private int id;
    private String question;

    public QuestionGetDto(int id, String question) {
        this.id = id;
        this.question = question;
    }

    public QuestionGetDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
