package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.comment.CommentForListDto;
import com.buzzardsview.readitcheck.model.dto.submission.SubmissionForListDto;
import com.buzzardsview.readitcheck.model.dto.user.UserGetDto;
import com.buzzardsview.readitcheck.model.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rest/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public UserGetDto getOne(@PathVariable String id) {
        User user = userRepository.getByGoogleId(id).orElseThrow(() ->
                new UserNotFoundException("user not found id-"+id));

        List<CommentForListDto> comments = user.getComments().stream().map(Comment::getCommentForUserList)
                .collect(Collectors.toList());

        Comparator<CommentForListDto> compareComByTimeStamp = Comparator.comparing(CommentForListDto::getTimestamp)
                .reversed();

        comments.sort(compareComByTimeStamp);

        List<SubmissionForListDto> submissions = user.getSubmissions().stream().map(Submission::getSubmissionForList)
                .collect(Collectors.toList());

        Comparator<SubmissionForListDto> compareSubByTimeStamp = Comparator.comparing(SubmissionForListDto::getTimestamp)
                .reversed();

        submissions.sort(compareSubByTimeStamp);

        return new UserGetDto(
                user.getGoogleId(),
                user.getUsername(),
                user.getPicture(),
                comments,
                submissions
        );

    }

}
