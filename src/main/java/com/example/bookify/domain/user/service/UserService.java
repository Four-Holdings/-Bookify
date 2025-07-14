package com.example.bookify.domain.user.service;

import com.example.bookify.domain.user.controller.dto.UserRequestDto;
import com.example.bookify.domain.user.controller.dto.UserResponseDto;
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
    public User register(UserRequestDto userRequestDto) {
        validateDuplicatedUser(userRequestDto);

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        User user = User.of(
                userRequestDto.getName(),
                userRequestDto.getEmail(),
                encodedPassword
        );
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Transactional
    public User delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.EXISTS_EMAIL));
        userRepository.delete(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User getMyInfo(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));
    }

    @Transactional
    public User updateMyInfo(Long userId, UserRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        validateDuplicatedUser(requestDto);

        user.updateInfo(requestDto.getName(), requestDto.getEmail());

        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
            user.updatePassword(encodedPassword);
        }
        return user;
    }

    private void validateDuplicatedUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new CustomException(ExceptionCode.EXISTS_EMAIL);
        }

        if (userRepository.existsByName(userRequestDto.getName())) {
            throw new CustomException(ExceptionCode.EXISTS_NAME);
        }
    }
}
