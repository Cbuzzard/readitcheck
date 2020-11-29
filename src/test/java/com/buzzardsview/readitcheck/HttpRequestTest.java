package com.buzzardsview.readitcheck;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void homeShouldReturnIndex() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("ReadItCheck");
    }

    @Test
    public void userShouldReturnIndex() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/user/1",
                String.class)).contains("ReadItCheck");
    }

    @Test
    public void submissionShouldReturnIndex() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/submission/1",
                String.class)).contains("ReadItCheck");
    }
}
