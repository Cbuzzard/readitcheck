package com.buzzardsview.readitcheck.controller;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private static User testUser;

    @BeforeAll
    public static void init() {
        testUser = new User("1", "name", "pic");
    }

    // (get) "/rest/user/{id}"
    @Test
    public void shouldFailWhenNoUserFound() throws Exception {
        when(userRepository.getByGoogleId("1")).thenReturn(Optional.empty());
        mockMvc.perform(get("/rest/user/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSucceedWhenUserExists() throws Exception {
        when(userRepository.getByGoogleId("1")).thenReturn(Optional.of(testUser));
        mockMvc.perform(get("/rest/user/1"))
                .andExpect(status().isOk());
    }

}
