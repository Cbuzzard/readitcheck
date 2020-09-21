package com.buzzardsview.readitcheck.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@NamedEntityGraph(
        name = "user-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("comments"),
        }
)
@Entity
public class User {

    //TODO figure out named entity graphs


    @Id
    @Size(max = 22)
    private String googleId;
    private String name;
    private String username;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Submission> submissions;
    @ManyToMany
    private List<Submission> submissionsApprovedOn;

    public User(String googleId, String name) {
        this.googleId = googleId;
        this.name = name;
        this.username = name;
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

    @JsonBackReference
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

    public void addSubmissionApprovedOn(Submission submission) {
        this.submissionsApprovedOn.add(submission);
    }
}
