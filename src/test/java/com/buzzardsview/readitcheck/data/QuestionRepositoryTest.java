package com.buzzardsview.readitcheck.data;

import com.buzzardsview.readitcheck.model.Question;
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
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    private static Question testQuestion;
    private static Submission testSubmission;
    private static User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User("2", "name", "pic");
        testSubmission = new Submission(testUser, "title", "https://www.google.com");
        testQuestion = new Question("q", "a", testSubmission);
        userRepository.saveAndFlush(testUser);
        submissionRepository.saveAndFlush(testSubmission);
        questionRepository.save(testQuestion);
    }

    @Test
    public void shouldFindNothingWhenTableIsEmpty() {
        questionRepository.deleteAllInBatch();
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(0);
    }

    @Test
    public void findAllShouldHaveOneQuestion() {
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(1);
    }

    @Test
    public void savedQuestionIsEqualToQuestionEntity() {
        Submission newSubmission = new Submission(testUser, "title", "https://www.google.com");
        Question newQuestion = new Question("question", "Answer", newSubmission);
        submissionRepository.saveAndFlush(newSubmission);
        Question savedQuestion = questionRepository.save(newQuestion);
        assertThat(savedQuestion.getQuestion()).isEqualTo(newQuestion.getQuestion());
        assertThat(savedQuestion.getSubmission().getTitle()).isEqualTo(newQuestion.getSubmission().getTitle());
    }

    @Test
    public void findByIdShouldReturnQuestion() {
        Question question = questionRepository.findById(testQuestion.getId()).orElseThrow();
        assertThat(question.getQuestion()).isEqualTo(testQuestion.getQuestion());
    }

    @Test
    public void shouldBeEmptyWhenQuestionNotFound() {
        Optional<Question> question = questionRepository.findById(testSubmission.getId()+100);
        assertThat(question).isEmpty();
    }


}
