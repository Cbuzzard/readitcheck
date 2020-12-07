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

public class CommentTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void emptyCommentShouldBeInvalid() {
        Comment comment = new Comment();
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyContentShouldBeInvalid() {
        User user = new User();
        Submission submission = new Submission();
        Comment comment = new Comment(user, "", submission);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void noAttachedSubmissionShouldBeInvalid() {
        User user = new User();
        Comment comment = new Comment();
        comment.setContent("content");
        comment.setUser(user);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void noAttachedUserShouldBeInvalid() {
        Submission submission = new Submission();
        Comment comment = new Comment();
        comment.setContent("content");
        comment.setSubmission(submission);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void OneCharacterContentShouldSucceed() {
        User user = new User();
        Submission submission = new Submission();
        Comment comment = new Comment(user, "1", submission);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void threeThousandCharacterContentShouldSucceed() {
        User user = new User();
        Submission submission = new Submission();
        Comment comment = new Comment(user, new String(new char[3000]).replace('\0',' '), submission);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void threeThousandAndOneCharacterContentShouldFail() {
        User user = new User();
        Submission submission = new Submission();
        Comment comment = new Comment(user, new String(new char[3001]).replace('\0',' '), submission);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void fullCommentShouldBeValid() {
        User user = new User();
        Submission submission = new Submission();
        Comment comment = new Comment(user, "content", submission);
        Set<ConstraintViolation<Comment>> violations = validator.validate(comment);
        assertTrue(violations.isEmpty());
    }
}
