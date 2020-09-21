package com.buzzardsview.readitcheck.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(
        name = "submission-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("comments"),
        }
)
@Entity
public class Submission extends Content {

    private String title;
    private String link;
    @OneToMany(mappedBy = "submission")
    private List<Comment> comments;
    @OneToMany(mappedBy = "submission")
    private List<Question> questions;
    @ManyToMany(mappedBy = "submissionsApprovedOn")
    private List<User> approvedUsers;

    public Submission() {
    }

    public Submission(User user, String title, String link, List<Question> questions) {
        super(user);
        this.title = title;
        this.link = link;
        this.questions = questions;
    }

    public Submission(User user, String title, String link) {
        super(user);
        this.title = title;
        this.link = link;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @JsonBackReference
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<User> getApprovedUsers() {
        return approvedUsers;
    }

    public void setApprovedUsers(List<User> approvedUsers) {
        this.approvedUsers = approvedUsers;
    }

    public void addApprovedUser(User user) {
        this.approvedUsers.add(user);
    }
}
