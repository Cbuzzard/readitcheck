package com.buzzardsview.readitcheck.model.dto;

public class CommentForListDto {

    private int id;
    private String content;
    private long timestamp;
    private UserSimpleDto user;

    public CommentForListDto(int id, String content, long timestamp, UserSimpleDto user) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.user = user;
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
}
