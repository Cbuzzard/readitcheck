package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.CommentRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.comment.CommentForListDto;
import com.buzzardsview.readitcheck.model.dto.comment.CommentPostDto;
import com.buzzardsview.readitcheck.model.exception.CommentNotFoundException;
import com.buzzardsview.readitcheck.model.exception.ForbiddenException;
import com.buzzardsview.readitcheck.model.exception.SubmissionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("rest/submission/{submissionId}/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public CommentForListDto newComment(@RequestBody @Valid CommentPostDto commentPostDto, @PathVariable Integer submissionId, ServletRequest request) {
        Submission submission = submissionRepository.getById(submissionId).orElseThrow(() ->
                new SubmissionNotFoundException("submission not found id-"+submissionId));
        User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();

        if (submission.getApprovedUsers().contains(user)) {
            Comment newComment = new Comment(user, commentPostDto.getContent(), submission);
            submission.addComment(newComment);
            commentRepository.save(newComment);
            submissionRepository.save(submission);
            return new CommentForListDto(newComment.getId(),
                    newComment.getContent(),
                    newComment.getTimestamp(),
                    newComment.getUser().getSimpleUser(),
                    newComment.getSubmission().getId()
            );
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("{commentId}")
    public void deleteComment(@PathVariable Integer submissionId, @PathVariable Integer commentId, ServletRequest request) {
        User user = userRepository.findById((String) request.getAttribute("userId")).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new CommentNotFoundException("comment not found id-"+commentId));

        if (user == comment.getUser()) {
            commentRepository.delete(comment);
        }
    }

}
