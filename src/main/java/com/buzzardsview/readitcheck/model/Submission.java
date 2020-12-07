package com.buzzardsview.readitcheck.model;

import com.buzzardsview.readitcheck.model.dto.submission.SubmissionForListDto;
import com.google.common.net.InternetDomainName;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Submission extends Content {

    @NotEmpty(message = "title cannot be empty")
    @Size(max = 255)
    private String title;
    @NotEmpty
    @Size(min = 1, max = 1000)
    @Pattern(regexp = "https?:\\/\\/(www\\.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)", message = "Link needs to be in https://www format")
    private String link;
    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @OneToOne(mappedBy = "submission", cascade=CascadeType.ALL)
    private Question question;
    @ManyToMany(mappedBy = "submissionsApprovedOn")
    private List<User> approvedUsers = new ArrayList<>();
    private String linkPreview;

    public Submission() {
    }

    public Submission(User user, String title, String link, Question question) {
        super(user);
        this.title = title;
        this.link = link;
        this.question = question;
        this.linkPreview = extractLinkPreviewInfo(this.link);
    }

    public Submission(User user, String title, String link) {
        super(user);
        this.title = title;
        this.link = link;
        this.linkPreview = extractLinkPreviewInfo(this.link);
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

    public String getLinkPreview() {
        return linkPreview;
    }

    public void setLinkPreview(String linkPreview) {
        this.linkPreview = linkPreview;
    }

    public SubmissionForListDto getSubmissionForList() {
        return new SubmissionForListDto(
                this.getId(),
                this.getTitle(),
                this.getLink(),
                this.getTimestamp(),
                this.getUser().getSimpleUser(),
                this.linkPreview
        );
    }

    public String extractLinkPreviewInfo(String url) {
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMetaTagContent(document, "meta[property=og:image]");
    }

    private String getMetaTagContent(Document document, String cssQuery) {
        Element elm = document.select(cssQuery).first();
        if (elm != null) {
            return elm.attr("content");
        }
        return "";
    }
}
