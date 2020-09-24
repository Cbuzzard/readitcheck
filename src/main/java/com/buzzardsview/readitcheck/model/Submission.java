package com.buzzardsview.readitcheck.model;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(
        name = "submission-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "user", subgraph = "user-subgraph"),
                @NamedAttributeNode("title")
        },
        subclassSubgraphs = {
                @NamedSubgraph(
                        name = "user-subgraph",
                        attributeNodes = {@NamedAttributeNode("googleId")}
                )
        }
)
@Entity
public class Submission extends Content {

    private String title;
    private String link;
    @OneToMany(mappedBy = "submission")
    private List<Comment> comments;
    @OneToOne(mappedBy = "submission", cascade=CascadeType.ALL)
    private Question question;
    @ManyToMany(mappedBy = "submissionsApprovedOn")
    private List<User> approvedUsers;

    public Submission() {
    }

    public Submission(User user, String title, String link, Question question) {
        super(user);
        this.title = title;
        this.link = link;
        this.question = question;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
