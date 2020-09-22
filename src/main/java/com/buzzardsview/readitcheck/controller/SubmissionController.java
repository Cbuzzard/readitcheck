package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<SubmissionForListDto> list(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {

        Page<Submission> submissionPage = submissionRepository.findAll(PageRequest.of(page, size, Sort.by("timestamp")));
        List<SubmissionForListDto> submissionsForListDto = submissionPage.getContent().stream().map(s ->
                        new SubmissionForListDto(
                                s.getId(),
                                s.getTitle(),
                                s.getLink(),
                                s.getTimestamp(),
                                s.getUser().getSimpleUser()
                        )).collect(Collectors.toList());

        return submissionsForListDto;
    }

}
