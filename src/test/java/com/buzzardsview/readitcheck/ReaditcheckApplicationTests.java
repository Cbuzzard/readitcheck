package com.buzzardsview.readitcheck;

import com.buzzardsview.readitcheck.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReaditcheckApplicationTests {

	@Autowired
	private AuthenticateController authenticateController;
	@Autowired
	private CommentController commentController;
	@Autowired
	private LoginController loginController;
	@Autowired
	private QuestionController questionController;
	@Autowired
	private SubmissionController submissionController;
	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
	}

	@Test
	void authControllerLoads() {
		assertThat(authenticateController).isNotNull();
	}

	@Test
	void CommentControllerLoads() {
		assertThat(commentController).isNotNull();
	}

	@Test
	void loginControllerLoads() {
		assertThat(loginController).isNotNull();
	}

	@Test
	void questionControllerLoads() {
		assertThat(questionController).isNotNull();
	}

	@Test
	void submissionControllerLoads() {
		assertThat(submissionController).isNotNull();
	}

	@Test
	void userControllerLoads() {
		assertThat(userController).isNotNull();
	}


}
