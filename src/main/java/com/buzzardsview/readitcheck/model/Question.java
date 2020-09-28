package com.buzzardsview.readitcheck.model;

import com.buzzardsview.readitcheck.model.dto.question.QuestionGetDto;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    @Size(max = 255)
    private String question;
    @NotEmpty(message = "must not be empty")
    @Size(max = 25)
    private String answer;
    @OneToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    public Question(String question, String answer, Submission submission) {
        this.question = question;
        this.answer = answer;
        this.submission = submission;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @JsonBackReference
    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public boolean checkAnswer(String answer) {
        return this.answer.toLowerCase().equals(answer.toLowerCase());
    }

    public QuestionGetDto getQuestionGet() {
        return new QuestionGetDto(this.id, this.question);
    }
}
