package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.question.QuestionCheckDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.ServletRequest;

@RestController
@RequestMapping("rest/question")
public class QuestionController {

    //TODO custom question not found exception

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("{id}")
    public boolean checkAnswer(@RequestBody String answer, @PathVariable Integer id, ServletRequest request) {
        System.out.println("hi");

        Question question = questionRepository.findById(id).orElseThrow();
        Submission submission = submissionRepository.getById(question.getSubmission().getId()).orElseThrow();

        System.out.println(answer);

        boolean answeredCorrectly = question.checkAnswer(answer);

        if (answeredCorrectly) {
            User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
            submission.addApprovedUser(user);
            user.addSubmissionApprovedOn(submission);
            submissionRepository.save(submission);
            userRepository.save(user);
        }

        return answeredCorrectly;

//        List<CommentForListDto> comments = submission.getComments().stream().map(c ->
//                new CommentForListDto(
//                        c.getId(),
//                        c.getContent(),
//                        c.getTimestamp(),
//                        c.getUser().getSimpleUser()
//                )
//        ).collect(Collectors.toList());
//
//        return new SubmissionGetDto(
//                submission.getId(),
//                submission.getUser().getSimpleUser(),
//                submission.getTitle(),
//                submission.getLink(),
//                submission.getQuestions(),
//                comments,
//                submission.getTimestamp(),
//                answeredCorrectly
//        );

    }

}
