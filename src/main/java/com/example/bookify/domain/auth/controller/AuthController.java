package com.example.bookify.domain.auth.controller;

import com.example.bookify.domain.auth.controller.dto.LoginRequest;
import com.example.bookify.domain.auth.controller.dto.LoginResponse;
import com.example.bookify.domain.auth.service.AuthService;
import com.example.bookify.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginApi(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ){
        LoginResponse login = authService.login(request);
        response.addHeader(HttpHeaders.AUTHORIZATION, login.getAccessToken());
        return ResponseEntity.ok(Map.of("token", login.getAccessToken()));
    }
}
