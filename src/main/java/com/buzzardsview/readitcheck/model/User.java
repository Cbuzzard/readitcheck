package com.buzzardsview.readitcheck.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamedEntityGraph(
        name = "user-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("submissions")
        }
)
@Entity
public class User {

    private long googleId;
    private String name;
    private String username;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @OneToMany(mappedBy = "user")
    private List<Submission> submissions;
    @ManyToMany
    private List<Submission> submissionsApprovedOn;

    public User(long googleId, String name) {
        this.googleId = googleId;
        this.name = name;
        this.username = name;
    }

    public long getGoogleId() {
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

    public void addSubmissionApprovedOn(Submission submission) {
        this.submissionsApprovedOn.add(submission);
    }
}
