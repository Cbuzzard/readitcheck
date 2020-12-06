package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.security.GoogleTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.buzzardsview.readitcheck.security.AppTokenProvider.getToken;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @MockBean
    private GoogleTokenVerifier googleTokenVerifier;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    // (post) "/login"
    @Test
    public void loginFailsWhenNoAuthHeader() throws Exception {
        mockMvc.perform(post("/login"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginFailWithIncorrectAuthHeader() throws Exception {
        mockMvc.perform(post("/login")
                .header("X-ID-TOKEN", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginSuccessWhenUserHasCorrectHeader() throws Exception {
        String token = getToken("1");
        GoogleIdToken.Payload payload = new GoogleIdToken.Payload();
        payload.setSubject("name");
        payload.set("name", "name");
        payload.set("picture", "pic");
        when(googleTokenVerifier.verify("1")).thenReturn(payload);
        mockMvc.perform(post("/login")
                .header("X-ID-TOKEN", "1"))
                .andExpect(status().isOk());
    }

}
