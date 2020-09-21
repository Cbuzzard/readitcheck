package com.buzzardsview.readitcheck.model.dto;

import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Question;

import java.util.List;

public class SubmissionGetDto {

    private int id;
    private String userId;
    private String title;
    private String link;
    private List<Question> questions;
    private List<Comment> comments;
    private long timestamp;
    private boolean currentUserApproved;

    public SubmissionGetDto(int id, String user, String title, String link, List<Question> questions, List<Comment> comments, long timestamp, boolean currentUserApproved) {
        this.id = id;
        this.userId = user;
        this.title = title;
        this.link = link;
        this.questions = questions;
        this.comments = comments;
        this.timestamp = timestamp;
        this.currentUserApproved = currentUserApproved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isCurrentUserApproved() {
        return currentUserApproved;
    }

    public void setCurrentUserApproved(boolean currentUserApproved) {
        this.currentUserApproved = currentUserApproved;
    }
}
