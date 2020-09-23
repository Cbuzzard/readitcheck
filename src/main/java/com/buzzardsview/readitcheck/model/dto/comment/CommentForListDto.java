package com.buzzardsview.readitcheck.model.dto.comment;

import com.buzzardsview.readitcheck.model.dto.user.UserSimpleDto;

public class CommentForListDto {

    private int id;
    private String content;
    private long timestamp;
    private UserSimpleDto user;
    private int submissionId;

    public CommentForListDto(int id, String content, long timestamp, UserSimpleDto user, int submissionId) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.user = user;
        this.submissionId = submissionId;
    }

    public CommentForListDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UserSimpleDto getUser() {
        return user;
    }

    public void setUser(UserSimpleDto user) {
        this.user = user;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }
}
