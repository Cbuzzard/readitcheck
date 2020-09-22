package com.buzzardsview.readitcheck.model.dto;

public class SubmissionForListDto {

    private int id;
    private String title;
    private String link;
    private long timestamp;
    private UserSimpleDto user;

    public SubmissionForListDto(int id, String title, String link, long timestamp, UserSimpleDto user) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.timestamp = timestamp;
        this.user = user;
    }

    public SubmissionForListDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
