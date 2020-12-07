package com.buzzardsview.readitcheck.data;


import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubmissionRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionRepository submissionRepository;


    private static Submission testSubmission;

    private static User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User("2", "name", "pic");
        testSubmission = new Submission(testUser, "title", "https://www.google.com");
        userRepository.saveAndFlush(testUser);
        submissionRepository.saveAndFlush(testSubmission);
    }

    @Test
    public void shouldFindNothingWhenTableIsEmpty() {
        submissionRepository.deleteAllInBatch();
        List<Submission> submissions = submissionRepository.findAll();
        assertThat(submissions).hasSize(0);
    }

    @Test
    public void findAllShouldHaveOneSubmission() {
        List<Submission> submissions = submissionRepository.findAll();
        assertThat(submissions).hasSize(1);
    }

    @Test
    public void savedSubmissionIsEqualToSubmissionEntity() {

        Submission newSubmission = new Submission(testUser, "title", "https://www.google.com");
        Submission savedSubmission = submissionRepository.save(newSubmission);
        assertThat(savedSubmission.getTitle()).isEqualTo(newSubmission.getTitle());
        assertThat(savedSubmission.getUser().getName()).isEqualTo(newSubmission.getUser().getName());
    }

    @Test
    public void findByIdShouldReturnSubmission() {
        Submission submission = submissionRepository.findById(testSubmission.getId()).orElseThrow();
        assertThat(submission.getTitle()).isEqualTo(testSubmission.getTitle());
    }

    @Test
    public void shouldBeEmptyWhenSubmissionNotFound() {
        Optional<Submission> submission = submissionRepository.findById(testSubmission.getId()+100);
        assertThat(submission).isEmpty();
    }

}
