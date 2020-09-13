package com.buzzardsview.readitcheck.model.dto;

public class CommentDto {

    private String content;
    private int submissionId;

    public CommentDto(String content, int submissionId) {
        this.content = content;
        this.submissionId = submissionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }
}
