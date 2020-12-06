package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static com.buzzardsview.readitcheck.security.AppTokenProvider.getToken;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private SubmissionRepository submissionRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private static User testUser;
    private static Submission testSubmission;
    private static Question testQuestion;

    @BeforeAll
    public static void init() {
        testUser = new User("1", "name", "pic");
        testUser.setSubmissionsApprovedOn(new ArrayList<>());
        testSubmission = new Submission(
                testUser,
                "title",
                "https://www.google.com"
        );
        testQuestion = new Question("q","a",testSubmission);
        testSubmission.setQuestion(testQuestion);
        testSubmission.setApprovedUsers(new ArrayList<>());
    }

    // (post) "/rest/question/{id}
    @Test
    public void checkAnswerShouldFailWhenUserIsNotLoggedIn() throws Exception {
        mockMvc.perform(post("/rest/question/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testQuestion)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void checkAnswerShouldFailWhenUserIsLoggedInButAnswersWrong() throws Exception {
        when(questionRepository.findById(1)).thenReturn(Optional.of(testQuestion));
        mockMvc.perform(post("/rest/question/1")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("false"));
    }

    @Test
    public void checkAnswerShouldSucceedWhenUserIsLoggedInAndAnswersCorrectly() throws Exception {
        when(questionRepository.findById(1)).thenReturn(Optional.of(testQuestion));
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        mockMvc.perform(post("/rest/question/1")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("a"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("true"));
    }

}
