package com.buzzardsview.readitcheck;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubmissionControllerTest {

    @MockBean
    private SubmissionRepository submissionRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private QuestionRepository questionRepository;

    @Autowired
    private MockMvc mockMvc;

    private static User testUser;
    private static Submission testSubmission;

    @BeforeAll
    public static void init() {
        testUser = new User("1","name", "pic");
        testSubmission = new Submission(
                testUser,
                "title",
                "https://www.google.com"
        );
        testSubmission.setQuestion(new Question("q","a",testSubmission));
    }

    @BeforeEach
    public void setup() {
        userRepository.save(testUser);
        submissionRepository.save(testSubmission);
    }

    @Test
    public void findAllReturnsOneSubmission() throws Exception {
        when(submissionRepository.findAll(PageRequest.of(0, 5, Sort.by("timestamp").descending()))).thenReturn(new PageImpl<>(Arrays.asList(testSubmission)));
        mockMvc.perform(get("/rest/submission").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
