package com.example.bookify.user;

import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_사용자_조회() throws Exception {
        // given
        User user = User.of("bookify", "test@gmail.com", "Test1234!");
        userRepository.save(user);

        // when
        Optional<User> find = userRepository.findByEmail("test@gmail.com");

        // then
        assertTrue(find.isPresent());
        assertEquals("test@gmail.com", find.get().getEmail());
    }
}
