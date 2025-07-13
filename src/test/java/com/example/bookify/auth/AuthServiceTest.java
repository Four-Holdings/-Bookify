package com.example.bookify.auth;

import com.example.bookify.domain.auth.controller.dto.LoginRequest;
import com.example.bookify.domain.auth.service.AuthService;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.domain.user.service.UserService;
import com.example.bookify.global.security.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void 로그인_성공시_JWT_반환(){
        // given
        LoginRequest request = new LoginRequest("Test@gmail.com", "Test1234!");
        User user = User.of("bookify", "Test@gmail.com", "encoded-password");

        when(userRepository.findByEmail("Test@gmail.com")).thenReturn(Optional.of(user));
        when(jwtUtil.createAccessToken(any())).thenReturn("jwt-token");

        // when
        String token = String.valueOf(authService.login(request));

        // then
        assertEquals("jwt-token", token);
    }
}
