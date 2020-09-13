package com.buzzardsview.readitcheck.model.dto;

public class CommentPostDto {

    //TODO maybe remove submissionId since it will be url param

    private String content;
    private int submissionId;

    public CommentPostDto(String content, int submissionId) {
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
