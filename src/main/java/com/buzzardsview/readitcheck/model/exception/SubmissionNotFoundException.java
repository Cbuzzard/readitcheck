package com.buzzardsview.readitcheck.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubmissionNotFoundException extends RuntimeException {

    public SubmissionNotFoundException(String exception) {
        super(exception);
    }

}
