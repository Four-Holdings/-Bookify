package com.example.bookify.domain.book.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF 끔
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 요청 인증 없이 허용
                )
                .build();
    }
}