package com.buzzardsview.readitcheck.model.dto.user;

import com.buzzardsview.readitcheck.model.dto.comment.CommentForListDto;
import com.buzzardsview.readitcheck.model.dto.submission.SubmissionForListDto;

import java.util.List;

public class UserGetDto {

    private String id;
    private String username;
    private String picture;
    private List<CommentForListDto> comments;
    private List<SubmissionForListDto> submissions;

    public UserGetDto(String id, String username, String picture, List<CommentForListDto> comments, List<SubmissionForListDto> submissions) {
        this.id = id;
        this.username = username;
        this.picture = picture;
        this.comments = comments;
        this.submissions = submissions;
    }

    public UserGetDto() {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<CommentForListDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentForListDto> comments) {
        this.comments = comments;
    }

    public List<SubmissionForListDto> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<SubmissionForListDto> submissions) {
        this.submissions = submissions;
    }
}
