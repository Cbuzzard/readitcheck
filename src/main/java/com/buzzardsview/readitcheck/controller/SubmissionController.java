package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.QuestionPostDto;
import com.buzzardsview.readitcheck.model.dto.SubmissionGetDto;
import com.buzzardsview.readitcheck.model.dto.SubmissionPostDto;
import com.buzzardsview.readitcheck.security.AppTokenProvider;
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
    public SubmissionGetDto getOne(@PathVariable Integer id, ServletRequest servletRequest) {

        Submission submission = submissionRepository.getById(id).orElseThrow();
        User user = userRepository.findById(AppTokenProvider.getCurrentUserId(servletRequest)).orElseThrow();

        boolean currentUserApproved = user == null ? false : submission.getApprovedUsers().contains(user);

        return new SubmissionGetDto(
                submission.getId(),
                submission.getUser(),
                submission.getTitle(),
                submission.getLink(),
                submission.getQuestions(),
                submission.getComments(),
                submission.getTimestamp(),
                currentUserApproved
        );
    }

    @PostMapping
    public void newSubmission(@RequestBody SubmissionPostDto submissionDto, ServletRequest servletRequest) {

        User user = userRepository.findById(AppTokenProvider.getCurrentUserId(servletRequest)).orElseThrow();
        Submission newSubmission = new Submission(user, submissionDto.getTitle(), submissionDto.getLink());

        List<Question> questions = new ArrayList<>();
        for (QuestionPostDto questionDto : submissionDto.getQuestions()) {
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
