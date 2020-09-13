package com.buzzardsview.readitcheck.model.dto;

import java.util.List;

public class SubmissionDto {

    private String title;
    private String link;
    private List<QuestionDto> questions;

    public SubmissionDto(String title, String link, List<QuestionDto> questions) {
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

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
