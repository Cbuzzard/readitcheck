package com.buzzardsview.readitcheck.model.dto;

import java.util.List;

public class SubmissionPostDto {

    private String title;
    private String link;
    private List<QuestionPostDto> questions;

    public SubmissionPostDto(String title, String link, List<QuestionPostDto> questions) {
        this.title = title;
        this.link = link;
        this.questions = questions;
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

    public List<QuestionPostDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionPostDto> questions) {
        this.questions = questions;
    }
}
