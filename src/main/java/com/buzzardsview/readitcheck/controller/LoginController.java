package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletRequest;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public void authenticate(ServletRequest request) {

        String userId = (String) request.getAttribute("userId");
        String name = (String) request.getAttribute("name");
        String picture = (String) request.getAttribute("picture");

        if (!userRepository.findById(userId).isPresent()) {
            userRepository.save(new User(userId, name, picture));
        } else {
            User user = userRepository.findById(userId).orElseThrow();
            user.setPicture(picture);
            userRepository.save(user);
        }

    }

}
