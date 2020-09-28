package com.buzzardsview.readitcheck.model.dto.question;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class QuestionPostDto {

    @NotEmpty
    @Size(max = 255)
    private String question;
    @NotEmpty(message = "must not be empty")
    @Size(max = 25)
    private String answer;

    public QuestionPostDto(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
