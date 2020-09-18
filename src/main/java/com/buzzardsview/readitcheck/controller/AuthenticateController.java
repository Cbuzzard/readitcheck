package com.buzzardsview.readitcheck.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController("rest/authenticate")
public class AuthenticateController {

    public boolean authenticateUser(ServletRequest servletRequest) {
        return true;
    }

}
