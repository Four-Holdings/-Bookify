package com.example.bookify.auth;

import com.example.bookify.domain.auth.controller.AuthController;
import com.example.bookify.domain.auth.controller.dto.LoginRequest;
import com.example.bookify.domain.auth.controller.dto.LoginResponse;
import com.example.bookify.domain.auth.service.AuthService;
import com.example.bookify.global.security.jwt.JwtUtil;
import com.example.bookify.global.filter.JwtFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class),
        excludeAutoConfiguration = { org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class }
)
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void 로그인_성공시_헤더에_JWT_반환() throws Exception {
        // given
        LoginResponse mockResponse = new LoginResponse("jwt-token");
        Mockito.when(authService.login(any(LoginRequest.class))).thenReturn(mockResponse);
        String requestBody = "{\"email\":\"test@gmail.com\",\"password\":\"Test1234!\"}";

        // when & then
        mvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer jwt-token"));
    }
}
