package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.CommentRepository;
import com.buzzardsview.readitcheck.data.SubmissionRepository;
import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.Comment;
import com.buzzardsview.readitcheck.model.Question;
import com.buzzardsview.readitcheck.model.Submission;
import com.buzzardsview.readitcheck.model.User;
import com.buzzardsview.readitcheck.model.dto.comment.CommentPostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.buzzardsview.readitcheck.security.AppTokenProvider.getToken;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private SubmissionRepository submissionRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private static User testUser;
    private static Submission testSubmission;
    private static Comment testComment;

    @BeforeAll
    public static void init() {
        testUser = new User("1","name", "pic");
        testSubmission = new Submission(
                testUser,
                "title",
                "https://www.google.com"
        );
        testComment = new Comment(testUser, "content", testSubmission);
        testComment.setId(1);
        testSubmission.setQuestion(new Question("q","a",testSubmission));
        testSubmission.setId(1);
        testSubmission.setComments(new ArrayList<>());
        testSubmission.addComment(testComment);
        testSubmission.setApprovedUsers(new ArrayList<>());
    }

    @BeforeEach
    public void setup() {
        testSubmission.setApprovedUsers(new ArrayList<>());
    }

    // (post) /rest/submission/{submissionId}/comment
    @Test
    public void newCommentShouldFailWhenUserIsNotLoggedIn() throws Exception {
        mockMvc.perform(post("/rest/submission/1/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newCommentShouldFailWhenUserIsNotApprovedToComment() throws Exception {
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));

        mockMvc.perform(post("/rest/submission/1/comment")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newCommentShouldSucceedWhenUserIsApprovedToComment() throws Exception {
        testSubmission.addApprovedUser(testUser);

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));

        mockMvc.perform(post("/rest/submission/1/comment")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().isOk());
    }

    @Test
    public void newCommentShouldFailWhenContentIsEmpty() throws Exception {
        testSubmission.addApprovedUser(testUser);

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(submissionRepository.getById(1)).thenReturn(Optional.of(testSubmission));

        mockMvc.perform(post("/rest/submission/1/comment")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        "",
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void newCommentShouldFailWhenSubmissionNotFound() throws Exception {
        testSubmission.addApprovedUser(testUser);

        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(submissionRepository.getById(1)).thenReturn(Optional.empty());

        mockMvc.perform(post("/rest/submission/1/comment")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

    // (delete) "/rest/submission/{id}/comment/{id}
    @Test
    public void deleteCommentShouldFailWhenUserIsNotLoggedIn() throws  Exception{
        mockMvc.perform(delete("/rest/submission/1/comment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteCommentShouldSucceedWhenUserIsLoggedIn() throws  Exception{
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(commentRepository.findById(1)).thenReturn(Optional.of(testComment));

        mockMvc.perform(delete("/rest/submission/1/comment/1")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCommentShouldFailWhenCommentNotFound() throws  Exception{
        when(userRepository.findById("1")).thenReturn(Optional.of(testUser));
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/rest/submission/1/comment/1")
                .header("Authorization", getToken("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteCommentShouldFailWhenUserIsNotTheOwnerOfTheComment() throws  Exception{
        when(userRepository.findById("2")).thenReturn(Optional.of(new User("2", "name", "img")));
        when(commentRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/rest/submission/1/comment/1")
                .header("Authorization", getToken("2"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CommentPostDto(
                        testComment.getContent(),
                        testComment.getSubmission().getId()))))
                .andExpect(status().is4xxClientError());
    }

}
