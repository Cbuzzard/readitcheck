package com.buzzardsview.readitcheck.model.dto.submission;

import com.buzzardsview.readitcheck.model.dto.comment.CommentForListDto;
import com.buzzardsview.readitcheck.model.dto.question.QuestionGetDto;
import com.buzzardsview.readitcheck.model.dto.user.UserSimpleDto;

import java.util.List;

public class SubmissionGetDto {

    private int id;
    private UserSimpleDto user;
    private String title;
    private String link;
    private QuestionGetDto question;
    private List<CommentForListDto> comments;
    private long timestamp;
    private boolean currentUserApproved;

    public SubmissionGetDto(int id, UserSimpleDto user, String title, String link, QuestionGetDto question, List<CommentForListDto> comments, long timestamp, boolean currentUserApproved) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.link = link;
        this.question = question;
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

    public UserSimpleDto getUser() {
        return user;
    }

    public void setUser(UserSimpleDto user) {
        this.user = user;
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

    public QuestionGetDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionGetDto question) {
        this.question = question;
    }

    public List<CommentForListDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentForListDto> comments) {
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
