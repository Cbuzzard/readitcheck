package com.buzzardsview.readitcheck.model;

import javax.persistence.*;
import java.util.List;

@NamedEntityGraph(
        name = "submission-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("questions")
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
