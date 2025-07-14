package com.example.bookify.domain.user.controller;

import com.example.bookify.domain.user.controller.dto.UserRequestDto;
import com.example.bookify.domain.user.controller.dto.UserResponseDto;
import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.service.UserService;
import com.example.bookify.global.common.apiresponse.ResponseDto;
import com.example.bookify.global.security.jwt.CustomPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Map<String, Object>>> register(@Valid @RequestBody UserRequestDto requestDto) {
        User user = userService.register(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto<>("회원가입이 완료되었습니다.", userData(user)));
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<ResponseDto<Map<String, Object>>> withdraw(
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        User user = userService.delete(principal.getUserId());

        return ResponseEntity.ok(new ResponseDto<>("회원탈퇴가 완료되었습니다.", userData(user)));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<Map<String, Object>>> getMyInfo(
            @AuthenticationPrincipal CustomPrincipal principal
    ) {
        User user = userService.getMyInfo(principal.getUserId());
        return ResponseEntity.ok(new ResponseDto<>("회원 정보 조회 성공", userData(user)));
    }

    @PatchMapping("/me")
    public ResponseEntity<ResponseDto<Map<String, Object>>> updateMyInfo(
            @AuthenticationPrincipal CustomPrincipal principal,
            @Valid @RequestBody UserRequestDto requestDto
    ) {
        User user = userService.updateMyInfo(principal.getUserId(), requestDto);
        return ResponseEntity.ok(new ResponseDto<>("회원 정보 수정 성공", userData(user)));
    }

    // 사용자 데이터 순서대로 나열
    private Map<String, Object> userData(User user) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", user.getId());
        data.put("email", user.getEmail());
        data.put("name", user.getName());
        data.put("createdAt", user.getCreatedAt());
        data.put("updatedAt", user.getUpdatedAt());
        return data;
    }
}
