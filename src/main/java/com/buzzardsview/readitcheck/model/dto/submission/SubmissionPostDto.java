package com.buzzardsview.readitcheck.model.dto.submission;

import com.buzzardsview.readitcheck.model.dto.question.QuestionPostDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SubmissionPostDto {

    @NotEmpty(message = "title cannot be empty")
    @Size(max = 255)
    private String title;
    @NotEmpty
    @Size(max = 1000)
    @Pattern(regexp = "https?:\\/\\/(www\\.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)", message = "Link needs to be in https://www format")
    private String link;
    @Valid
    private QuestionPostDto question;

    public SubmissionPostDto(String title, String link, QuestionPostDto question) {
        this.title = title;
        this.link = link;
        this.question = question;
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

    public QuestionPostDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionPostDto question) {
        this.question = question;
    }
}
