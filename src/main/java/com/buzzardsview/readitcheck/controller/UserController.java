package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest/user")
public class UserController {

    //TODO custom User not found exception

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User getOne(@PathVariable String id) {
        return userRepository.getByGoogleId(id).orElseThrow();
    }

}
