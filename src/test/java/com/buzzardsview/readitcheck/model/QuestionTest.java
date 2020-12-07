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

public class QuestionTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fullQuestionShouldSucceed() {
        Question question = new Question("question", "answer", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void fullyEmptyQuestionShouldFail() {
        Question question = new Question();
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyQuestionFieldShouldFail() {
        Question question = new Question("", "answer", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyAnswerFieldShouldFail() {
        Question question = new Question("question", "", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullSubmissionShouldFail() {
        Question question = new Question();
        question.setAnswer("answer");
        question.setQuestion("question");
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void answerWithLengthOneShouldSucceed() {
        Question question = new Question("question", "a", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void answerWithLengthTwentyFiveShouldSucceed() {
        Question question = new Question("question", new String(new char[25]).replace('\0',' '), new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void answerWithLengthTwentySixShouldFail() {
        Question question = new Question("question", new String(new char[26]).replace('\0',' '), new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void questionWithLengthOneShouldSucceed() {
        Question question = new Question("q", "answer", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void questionWithLengthTwoHundredFiftyFiveShouldSucceed() {
        Question question = new Question(new String(new char[255]).replace('\0',' '),"answer", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void questionWithLengthTwoHundredFiftySixShouldFail() {
        Question question = new Question(new String(new char[256]).replace('\0',' '),"answer", new Submission());
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void checkAnswerShouldReturnTrueWhenAnswersMatch() {
        Question question = new Question("question", "answer", new Submission());
        assertTrue(question.checkAnswer("answer"));
    }

    @Test
    public void checkAnswerShouldReturnFalseWhenAnswersDoNotMatch() {
        Question question = new Question("question", "answer", new Submission());
        assertFalse(question.checkAnswer("a"));
    }

    @Test
    public void checkAnswerShouldReturnTrueWhenAnswersMatchAndHaveDifferentCapitalization() {
        Question question = new Question("question", "answer", new Submission());
        assertTrue(question.checkAnswer("ANSWER"));
    }



}
