package com.buzzardsview.readitcheck.model.dto.submission;

import com.buzzardsview.readitcheck.model.dto.question.QuestionPostDto;

public class SubmissionPostDto {

    private String title;
    private String link;
    private QuestionPostDto question;

    public SubmissionPostDto(String title, String link, QuestionPostDto question) {
        this.title = title;
        this.link = link;
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public QuestionPostDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionPostDto question) {
        this.question = question;
    }
}
