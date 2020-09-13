package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.CommentDto;
import com.buzzardsview.readitcheck.model.dto.QuestionDto;
import com.buzzardsview.readitcheck.model.dto.SubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rest/submission")
public class SubmissionController {

    //TODO custom submission not found exception

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public Submission getOne(@PathVariable Integer id) {
        return submissionRepository.getById(id).orElseThrow();
    }

    @PostMapping
    public void newSubmission(@RequestBody SubmissionDto submissionDto, ServletRequest servletRequest) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userId = (String) request.getAttribute("userId");
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow();
        Submission newSubmission = new Submission(user, submissionDto.getTitle(), submissionDto.getLink());

        List<Question> questions = new ArrayList<>();
        for (QuestionDto questionDto : submissionDto.getQuestions()) {
            questions.add(new Question(questionDto.getQuestion(), questionDto.getAnswer(), newSubmission));
        }

        newSubmission.setQuestions(questions);
        submissionRepository.save(newSubmission);
    }

    @GetMapping
    public List<Submission> getAll() {
        return submissionRepository.findAll();
    }

}
