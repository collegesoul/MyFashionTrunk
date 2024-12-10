package com.example.myFashionTrunk.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .name("John")
                .surname("Doe")
                .email("john@example.com")
                .password("password")
                .build();
        userRepo.save(user);
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        Optional<User> foundUser = userRepo.findByEmail("john@example.com");
        assertThat(foundUser)
                .isPresent()
                .get()
                .extracting(User::getEmail)
                .isEqualTo("john@example.com");
    }

    @Test
    public void whenFindByNonExistentEmail_thenReturnEmptyOptional() {
        Optional<User> foundUser = userRepo.findByEmail("johndoe@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue() {
        boolean exists = userRepo.existsByEmail("john@example.com");
        assertThat(exists).isTrue();
    }

}
