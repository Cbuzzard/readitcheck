package com.buzzardsview.readitcheck.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubmissionTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fullSubmissionShouldBeValid() {
        Submission submission = new Submission(new User(), "title", "https://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void emptyTitleShouldBeInvalid() {
        Submission submission = new Submission(new User(), "", "https://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void titleWithOneCharacterShouldBeValid() {
        Submission submission = new Submission(new User(), "t", "https://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void titleWithTwoHundredAndFiftyFiveCharactersShouldBeValid() {
        Submission submission = new Submission(new User(), new String(new char[255]).replace('\0',' '), "https://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void titleWithTwoHundredAndFiftySixCharactersShouldBeInvalid() {
        Submission submission = new Submission(new User(), new String(new char[256]).replace('\0',' '), "https://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyLinkShouldBeInvalid() {
        assertThrows(IllegalArgumentException.class,()->{
            Submission submission = new Submission(new User(), "title", "", new Question());
        });
    }

    @Test
    public void linkWithOneCharacterShouldBeInvalid() {
        assertThrows(NullPointerException.class,()->{
            Submission submission = new Submission(new User(), "title", "h", new Question());
        });
    }

    @Test
    public void linkWithoutWWWShouldBeInvalid() {
        Submission submission = new Submission(new User(), "title", "https://google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void linkWithoutHttpShouldBeInvalid() {
        Submission submission = new Submission(new User(), "title", "www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void linkWithoutHttpOrWWWShouldBeInvalid() {
        Submission submission = new Submission(new User(), "title", "google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void linkWithHttpAndWWWShouldBeValid() {
        Submission submission = new Submission(new User(), "title", "http://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void linkWithHttpsAndWWWShouldBeValid() {
        Submission submission = new Submission(new User(), "title", "https://www.google.com", new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void linkWithoutDotShouldBeInvalid() {
        assertThrows(NullPointerException.class,()->{
            Submission submission = new Submission(new User(), "title", "https://www.googlecom", new Question());
        });
    }

    @Test
    public void linkWithoutSomethingAfterTheDotShouldBeInvalid() {

        assertThrows(NullPointerException.class,()->{
            Submission submission = new Submission(new User(), "title", "https://www.google.", new Question());
        });
    }

    @Test
    public void nullUserShouldBeInvalid() {
        Submission submission = new Submission();
        submission.setTitle("title");
        submission.setLink("https://www.google.com");
        submission.setQuestion(new Question());
        Set<ConstraintViolation<Submission>> violations = validator.validate(submission);
        assertFalse(violations.isEmpty());
    }

}
