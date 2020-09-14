package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.QuestionCheckDto;
import com.buzzardsview.readitcheck.model.dto.SubmissionGetDto;
import com.buzzardsview.readitcheck.security.AppTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController("rest/submission/{submissionId}/question")
public class QuestionController {

    //TODO custom question not found exception

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("{questionId}")
    public SubmissionGetDto checkAnswer(QuestionCheckDto questionCheckDto, @PathVariable Integer submissionId,
                                        @PathVariable Integer questionId, ServletRequest servletRequest) {

        Question question = questionRepository.findById(questionId).orElseThrow();
        Submission submission = submissionRepository.getById(submissionId).orElseThrow();

        boolean answeredCorrectly = question.checkAnswer(questionCheckDto.getAnswer());

        if (answeredCorrectly) {
            User user = userRepository.findById(AppTokenProvider.getCurrentUserId(servletRequest)).orElseThrow();
            submission.addApprovedUser(user);
            user.addSubmissionApprovedOn(submission);
            submissionRepository.save(submission);
            userRepository.save(user);
        }

        return new SubmissionGetDto(
                submission.getId(),
                submission.getUser(),
                submission.getTitle(),
                submission.getLink(),
                submission.getQuestions(),
                submission.getComments(),
                submission.getTimestamp(),
                answeredCorrectly
        );

    }

}
