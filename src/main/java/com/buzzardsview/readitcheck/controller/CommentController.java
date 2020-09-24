package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.CommentRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.comment.CommentForListDto;
import com.buzzardsview.readitcheck.model.dto.comment.CommentPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping("rest/submission/{submissionId}/comment")
public class CommentController {

    //TODO add custom submission not found exception

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public CommentForListDto newComment(@RequestBody String content, @PathVariable Integer submissionId, ServletRequest request) {
        Submission submission = submissionRepository.getById(submissionId).orElseThrow();
        User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();

        if (submission.getApprovedUsers().contains(user)) {
            Comment newComment = new Comment(user, content, submission);
            submission.addComment(newComment);
            commentRepository.save(newComment);
            submissionRepository.save(submission);
            return new CommentForListDto(newComment.getId(),
                    newComment.getContent(),
                    newComment.getTimestamp(),
                    newComment.getUser().getSimpleUser(),
                    newComment.getSubmission().getId()
            );
        }

        return null;
    }

}
