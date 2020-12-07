package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    private static Comment testComment;

    private static Submission testSubmission;

    private static User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User("2", "name", "pic");
        testSubmission = new Submission(testUser, "title", "https://www.google.com");
        testComment = new Comment(
                testUser,
                "cont",
                testSubmission
        );
        testSubmission.addComment(testComment);
        testUser.addComment(testComment);
        userRepository.saveAndFlush(testUser);
        submissionRepository.saveAndFlush(testSubmission);
        commentRepository.save(testComment);
    }

    @Test
    public void shouldFindNothingWhenTableIsEmpty() {
        commentRepository.deleteAllInBatch();
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(0);
    }

    @Test
    public void findAllShouldHaveOneComment() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).hasSize(1);
    }

    @Test
    public void savedCommentIsEqualToCommentEntity() {
        Comment newComment = new Comment(testUser, "content", testSubmission);
        Comment savedComment = commentRepository.save(newComment);
        assertThat(savedComment.getContent()).isEqualTo(newComment.getContent());
        assertThat(savedComment.getUser().getName()).isEqualTo(newComment.getUser().getName());
        assertThat(savedComment.getSubmission().getTitle()).isEqualTo(newComment.getSubmission().getTitle());
    }

    @Test
    public void findByIdShouldReturnComment() {
        Comment comment = commentRepository.findById(testComment.getId()).orElseThrow();
        assertThat(comment.getContent()).isEqualTo(testComment.getContent());
    }

    @Test
    public void shouldBeEmptyWhenCommentNotFound() {
        Optional<Comment> comment = commentRepository.findById(testComment.getId()+100);
        assertThat(comment).isEmpty();
    }

}
