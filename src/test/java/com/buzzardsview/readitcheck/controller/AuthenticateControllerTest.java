package com.buzzardsview.readitcheck.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.buzzardsview.readitcheck.security.AppTokenProvider.getToken;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    private void authFailsWithNoAuthHeader() throws Exception {
        mockMvc.perform(get("/rest/authenticate"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    private void authFailsWithInvalidAuthHeader() throws Exception {
        mockMvc.perform(get("/rest/authenticate")
                .header("1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    private void authSucceedsWithValidAuthHeader() throws Exception {
        mockMvc.perform(get("/rest/authenticate")
                .header(getToken("1")))
                .andExpect(status().isOk());
    }

}
