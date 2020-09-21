package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.QuestionPostDto;
import com.buzzardsview.readitcheck.model.dto.SubmissionGetDto;
import com.buzzardsview.readitcheck.model.dto.SubmissionPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
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
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/{id}")
    public SubmissionGetDto getOne(@PathVariable Integer id, ServletRequest request) {

        User user = null;

        Submission submission = submissionRepository.getById(id).orElseThrow();
        if (((String) request.getAttribute("userId")) != null) {
            user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
        }

        boolean currentUserApproved = user == null ? false : submission.getApprovedUsers().contains(user);

        return new SubmissionGetDto(
                submission.getId(),
                submission.getUser().getGoogleId(),
                submission.getTitle(),
                submission.getLink(),
                submission.getQuestions(),
                submission.getComments(),
                submission.getTimestamp(),
                currentUserApproved
        );
    }

    @PostMapping
    public Integer newSubmission(@RequestBody SubmissionPostDto submissionDto, ServletRequest request) {

        User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
        Submission newSubmission = new Submission(user, submissionDto.getTitle(), submissionDto.getLink());

        List<Question> questions = new ArrayList<>();
        for (QuestionPostDto questionDto : submissionDto.getQuestions()) {
            questions.add(new Question(questionDto.getQuestion(), questionDto.getAnswer(), newSubmission));
        }

        newSubmission.setQuestions(questions);
        submissionRepository.save(newSubmission);
        questionRepository.saveAll(questions);
        submissionRepository.flush();
        return newSubmission.getId();
    }

    @GetMapping
    public List<Submission> getAll() {
        return submissionRepository.findAll();
    }

}
