package com.buzzardsview.readitcheck.model.dto;

public class UserSimpleDto {

    private String id;
    private String username;

    public UserSimpleDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserSimpleDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
