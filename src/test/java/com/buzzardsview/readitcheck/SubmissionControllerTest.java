package com.buzzardsview.readitcheck;

import com.buzzardsview.readitcheck.data.QuestionRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.question.QuestionPostDto;
import com.buzzardsview.readitcheck.model.dto.submission.SubmissionPostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.buzzardsview.readitcheck.security.AppTokenProvider.getToken;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SubmissionControllerTest {

    @MockBean
    private SubmissionRepository submissionRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

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
        testSubmission.setId(1);
        List<Comment> testComments = new ArrayList<>();
        testComments.add(new Comment(testUser, "hi", testSubmission));
        testSubmission.setComments(testComments);
        testSubmission.setApprovedUsers(new ArrayList<>());
    }

    // (get) "/rest/submission/{id}"
    @Test
    public void getOneReturnsCorrectSubmission() throws Exception {
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));

        mockMvc.perform(get("/rest/submission/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testSubmission.getTitle()))
                .andExpect(jsonPath("$.currentUserApproved").value("false"));
    }

    @Test
    public void getOneDoesNotReturnSubmissionThatDoesNotExist() throws Exception {
        when(submissionRepository.getById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rest/submission/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getOneReturnsCurrentUserApproved() throws Exception {
        testSubmission.addApprovedUser(testUser);

        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/rest/submission/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken("1")))
                .andExpect(jsonPath("$.currentUserApproved").value("true"));
    }

    // (post) "/rest/submission"
    @Test
    public void newSubmissionWillFailWithNoLoggedInUser() throws Exception {
        SubmissionPostDto submissionPost = new SubmissionPostDto(
                "test",
                "https://www.google.com",
                new QuestionPostDto("q","a"));

        mockMvc.perform(post("/rest/submission")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submissionPost)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newSubmissionWillSucceedWithLoggedInUser() throws Exception {
        SubmissionPostDto submissionPost = new SubmissionPostDto(
                "test",
                "https://www.google.com",
                new QuestionPostDto("q","a"));

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/rest/submission")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submissionPost)))
                .andExpect(status().isOk());
    }

    @Test
    public void newSubmissionWillFailWithIncorrectLink() throws Exception {
        SubmissionPostDto submissionPost = new SubmissionPostDto(
                "test",
                "",
                new QuestionPostDto("q","a"));

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/rest/submission")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submissionPost)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newSubmissionWillFailWithIncorrectTitle() throws Exception {
        SubmissionPostDto submissionPost = new SubmissionPostDto(
                "",
                "https://www.google.com",
                new QuestionPostDto("q","a"));

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/rest/submission")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submissionPost)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newSubmissionWillFailWithIncorrectQuestion() throws Exception {
        SubmissionPostDto submissionPost = new SubmissionPostDto(
                "test",
                "https://www.google.com",
                new QuestionPostDto("","a"));

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/rest/submission")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submissionPost)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newSubmissionWillFailWithIncorrectAnswer() throws Exception {
        SubmissionPostDto submissionPost = new SubmissionPostDto(
                "test",
                "https://www.google.com",
                new QuestionPostDto("q",""));

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));

        mockMvc.perform(post("/rest/submission")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(submissionPost)))
                .andExpect(status().is4xxClientError());
    }

    // (get) "/rest/submission
    @Test
    public void findAllReturnsOneSubmission() throws Exception {
        when(submissionRepository.findAll(PageRequest.of(0, 5, Sort.by("timestamp").descending())))
                .thenReturn(new PageImpl<>(Arrays.asList(testSubmission)));
        mockMvc.perform(get("/rest/submission")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value(testSubmission.getTitle()));
    }

    @Test
    public void findAllPagesReturnsSizeFiveOnPageOneAndSizeTwoOnPageTwo() throws Exception {
        List<Submission> testSubmissions = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Submission newSubmission = new Submission(
                    testUser,
                    "title" + i,
                    "https://www.google.com"
            );
            newSubmission.setQuestion(new Question("q","a",testSubmission));
            newSubmission.setId(i);
            testSubmissions.add(newSubmission);
        }

        when(submissionRepository.findAll(PageRequest.of(0, 5, Sort.by("timestamp").descending())))
                .thenReturn(new PageImpl<>(testSubmissions.subList(0,  5)));
        when(submissionRepository.findAll(PageRequest.of(1, 5, Sort.by("timestamp").descending())))
                .thenReturn(new PageImpl<>(testSubmissions.subList(5, testSubmissions.size())));

        mockMvc.perform(get("/rest/submission")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
        mockMvc.perform(get("/rest/submission?page=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // (delete) /rest/submission/{id}
    @Test
    public void ShouldDeleteSubmissionThatHasSameUserId() throws Exception {
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));
        mockMvc.perform(delete("/rest/submission/1")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void ShouldNotDeleteSubmissionWhenUserIsNotLoggedIn() throws Exception {
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));
        mockMvc.perform(delete("/rest/submission/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void ShouldNotDeleteSubmissionThatHasDifferentUserId() throws Exception {
        when(userRepository.findById("2")).thenReturn(Optional.of(new User("2", "name", "img")));
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));
        mockMvc.perform(delete("/rest/submission/1")
                .header("Authorization", getToken("2"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
