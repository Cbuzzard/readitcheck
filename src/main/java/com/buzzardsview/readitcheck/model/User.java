package com.buzzardsview.readitcheck.model;

import com.buzzardsview.readitcheck.model.dto.user.UserSimpleDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @Size(max = 22)
    @NotEmpty
    private String googleId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String username;
    @NotEmpty
    private String picture;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Submission> submissions = new ArrayList<>();
    @ManyToMany
    private List<Submission> submissionsApprovedOn= new ArrayList<>();

    public User(String googleId, String name, String profilePic) {
        this.googleId = googleId;
        this.name = name;
        this.username = name;
        this.picture = profilePic;
    }

    public User() {
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
    }

    public List<Submission> getSubmissionsApprovedOn() {
        return submissionsApprovedOn;
    }

    public void setSubmissionsApprovedOn(List<Submission> submissionsApprovedOn) {
        this.submissionsApprovedOn = submissionsApprovedOn;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void addSubmissionApprovedOn(Submission submission) {
        this.submissionsApprovedOn.add(submission);
    }

    public UserSimpleDto getSimpleUser() {
        return new UserSimpleDto(this.googleId, this.username);
    }
}
