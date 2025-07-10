package com.example.bookify.domain.auth.service;

import com.example.bookify.domain.auth.controller.dto.LoginRequest;
import com.example.bookify.domain.auth.controller.dto.LoginResponse;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import com.example.bookify.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionCode.INVALID_EMAIL_OR_PASSWORD));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }

        String accessToken = jwtUtil.createAccessToken(user.getId());
        return new LoginResponse(accessToken);
    }
}
