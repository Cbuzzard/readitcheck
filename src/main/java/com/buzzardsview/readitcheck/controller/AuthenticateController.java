package com.buzzardsview.readitcheck.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.buzzardsview.readitcheck.security.AppTokenProvider.getUserFromToken;

@RestController
@RequestMapping("rest/authenticate")
public class AuthenticateController {

    @GetMapping("")
    public boolean authenticateUser(HttpServletRequest request, ServletResponse servletResponse) {
        Optional<String> userFromToken;
        try {
            userFromToken = getUserFromToken(request);
            return userFromToken.isPresent();
        } catch (Throwable err) {
            return false;
        }
    }

}
