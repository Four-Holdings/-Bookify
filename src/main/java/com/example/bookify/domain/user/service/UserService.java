package com.example.bookify.domain.user.service;

import com.example.bookify.domain.user.controller.dto.RegisterRequestDto;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequestDto registerRequestDto) {
        validateDuplicatedUser(registerRequestDto);

        String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        User user = User.of(
                registerRequestDto.getName(),
                registerRequestDto.getEmail(),
                encodedPassword
        );
        userRepository.save(user);
    }

    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.EXISTS_EMAIL));
        userRepository.delete(user);
    }

    private void validateDuplicatedUser(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new CustomException(ExceptionCode.EXISTS_EMAIL);
        }

        if (userRepository.existsByName(registerRequestDto.getName())) {
            throw new CustomException(ExceptionCode.EXISTS_NAME);
        }
    }
}
