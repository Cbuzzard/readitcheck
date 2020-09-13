package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    public void authenticate(ServletRequest servletRequest) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        String name = (String) request.getAttribute("name");

        if (!userRepository.findById(userId).isPresent()) {
            userRepository.save(new User(userId, name));
        }

    }

}
