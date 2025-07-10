package com.example.bookify.global.filter;

import com.example.bookify.domain.user.domain.model.User;
import com.example.bookify.domain.user.domain.repository.UserRepository;
import com.example.bookify.global.common.exception.enums.ExceptionCode;
import com.example.bookify.global.common.exception.exceptionclass.CustomException;
import com.example.bookify.global.security.jwt.CustomPrincipal;
import com.example.bookify.global.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private static final String BEARER_PREFIX ="Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Http Reqeust Header에서 필요한 JWT를 추출
        // 2. 인증된 사용자 정보를 저장
        String bearerJwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerJwt == null || !bearerJwt.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = jwtUtil.subStringToken(bearerJwt);

        if (!jwtUtil.validateToken(jwt)) {
            throw new CustomException(ExceptionCode.INVALID_OR_MISSING_TOKEN);
        }

        String subject = jwtUtil.extractUserId(jwt);
        Long userId = Long.parseLong(subject);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        // CustomPrincipal 적용
        CustomPrincipal principal = new CustomPrincipal(user.getId(), user.getName());

        // 인증 정보 SecurityContext에 저장
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}

