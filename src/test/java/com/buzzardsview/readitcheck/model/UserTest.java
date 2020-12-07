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

public class UserTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fullTestShouldBeValid() {
        User user = new User("123", "name", "pic");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void emptyGoogleIdShouldBeInvalid() {
        User user = new User("", "name", "pic");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void googleIdWithOneCharShouldBeValid() {
        User user = new User("1", "name", "pic");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void googleIdWithTwentyTwoCharsShouldBeValid() {
        User user = new User(new String(new char[22]).replace('\0',' '), "name", "pic");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void googleIdWithTwentyThreeCharsShouldBeInvalid() {
        User user = new User(new String(new char[23]).replace('\0',' '), "name", "pic");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyNameShouldBeInvalid() {
        User user = new User("123", "", "pic");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyPictureShouldBeInvalid() {
        User user = new User("123", "name", "");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

}
