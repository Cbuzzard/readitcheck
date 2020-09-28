package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletRequest;

@RestController
@RequestMapping("rest/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("{id}")
    public boolean checkAnswer(@RequestBody String answer, @PathVariable Integer id, ServletRequest request) {

        Question question = questionRepository.findById(id).orElseThrow();
        Submission submission = submissionRepository.getById(question.getSubmission().getId()).orElseThrow();

        boolean answeredCorrectly = question.checkAnswer(answer);

        if (answeredCorrectly) {
            User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
            submission.addApprovedUser(user);
            user.addSubmissionApprovedOn(submission);
            submissionRepository.save(submission);
            userRepository.save(user);
        }

        return answeredCorrectly;
    }

}
