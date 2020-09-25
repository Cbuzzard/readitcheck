package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.comment.CommentForListDto;
import com.buzzardsview.readitcheck.model.dto.submission.SubmissionForListDto;
import com.buzzardsview.readitcheck.model.dto.submission.SubmissionGetDto;
import com.buzzardsview.readitcheck.model.dto.submission.SubmissionPostDto;
import com.buzzardsview.readitcheck.model.exception.SubmissionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Comparator;
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

        Submission submission = submissionRepository.getById(id).orElseThrow(()->
                new SubmissionNotFoundException("id-"));
        if ((request.getAttribute("userId")) != null) {
            user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
        }

        boolean currentUserApproved = user != null && submission.getApprovedUsers().contains(user);



        List<CommentForListDto> comments = submission.getComments().stream().map(
                Comment::getCommentForList
        ).collect(Collectors.toList());

        Comparator<CommentForListDto> compareByTimeStamp = Comparator.comparing((CommentForListDto o) -> ((Long) o.getTimestamp())).reversed();

        comments.sort(compareByTimeStamp);

        return new SubmissionGetDto(
                submission.getId(),
                submission.getUser().getSimpleUser(),
                submission.getTitle(),
                submission.getLink(),
                submission.getQuestion().getQuestionGet(),
                comments,
                submission.getTimestamp(),
                currentUserApproved
        );
    }

    @PostMapping
    public Integer newSubmission(@RequestBody SubmissionPostDto submissionPostDto, ServletRequest request) {

        User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
        Submission newSubmission = new Submission(user, submissionPostDto.getTitle(), submissionPostDto.getLink());

        Question question = new Question(
                submissionPostDto.getQuestion().getQuestion(),
                submissionPostDto.getQuestion().getAnswer(),
                newSubmission
        );

        newSubmission.setQuestion(question);
        submissionRepository.save(newSubmission);
        return newSubmission.getId();
    }

    @GetMapping
    public List<SubmissionForListDto> list(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size) {

        Page<Submission> submissionPage = submissionRepository.findAll(PageRequest.of(page, size, Sort.by("timestamp").descending()));
        List<SubmissionForListDto> submissionsForListDto = submissionPage.getContent().stream().map(
                Submission::getSubmissionForList
        ).collect(Collectors.toList());

        return submissionsForListDto;
    }

    @DeleteMapping("{id}")
    public void deleteSubmission(@PathVariable Integer id, ServletRequest request) {
        User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
        Submission submission = submissionRepository.getById(id).orElseThrow();

        if(user == submission.getUser()) {
            submissionRepository.delete(submission);
        }

    }
}
