package com.buzzardsview.readitcheck.model;

import javax.persistence.*;

@Entity
public class Comment extends Content {

    private String content;
    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    public Comment(User user, String content, Submission submission) {
        super(user);
        this.content = content;
        this.submission = submission;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
