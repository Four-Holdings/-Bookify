package com.example.bookify.global.security.jwt;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;

@Getter
public class CustomPrincipal extends UsernamePasswordAuthenticationToken {
    private final Long userId;
    private final String username;

    public CustomPrincipal(Long userId, String username){
        super(null, null, Collections.emptyList());
        this.userId = userId;
        this.username = username;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
