package com.example.bookify.domain.user.controller;

import com.example.bookify.domain.user.controller.dto.RegisterRequestDto;
import com.example.bookify.domain.user.service.UserService;
import com.example.bookify.global.security.jwt.CustomPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Void>> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        userService.register(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<>("회원가입이 완료되었습니다.", null));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ResponseDto<Void>> withdraw(
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        userService.delete(principal.getUserId());

        return ResponseEntity.ok(new ResponseDto<>("회원 탈퇴가 완료되었습니다.", null));
    }
}
