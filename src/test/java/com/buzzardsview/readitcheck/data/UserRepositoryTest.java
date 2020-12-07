package com.buzzardsview.readitcheck.data;

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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User("2", "name", "pic");
        userRepository.saveAndFlush(testUser);
    }

    @Test
    public void shouldFindNothingWhenTableIsEmpty() {
        userRepository.deleteAllInBatch();
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(0);
    }

    @Test
    public void findAllShouldHaveOneUser() {
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }

    @Test
    public void savedUserIsEqualToUserEntity() {
        User newUser = new User("3","name","pic");
        User savedUser = userRepository.save(newUser);
        assertThat(savedUser.getGoogleId()).isEqualTo(newUser.getGoogleId());
        assertThat(savedUser.getName()).isEqualTo(newUser.getName());
    }

    @Test
    public void findByIdShouldReturnUser() {
        User user = userRepository.findById(testUser.getGoogleId()).orElseThrow();
        assertThat(user.getGoogleId()).isEqualTo(testUser.getGoogleId());
    }

    @Test
    public void shouldBeEmptyWhenUserNotFound() {
        Optional<User>  user = userRepository.findById("100");
        assertThat(user).isEmpty();
    }

}
