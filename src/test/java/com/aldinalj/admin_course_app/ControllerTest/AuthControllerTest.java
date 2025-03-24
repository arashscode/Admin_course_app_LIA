/*package com.aldinalj.admin_course_app.ControllerTest;

import com.aldinalj.admin_course_app.controller.AuthController;
import com.aldinalj.admin_course_app.model.LoginRequest;
import com.aldinalj.admin_course_app.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnJwtTokenWhenLoginIsSuccessful() throws Exception {
        LoginRequest request = new LoginRequest("admin@admin.com", "admin123");
        Authentication mockAuth = new UsernamePasswordAuthenticationToken(request.username(), null);
        String mockToken = "mocked-jwt-token";

        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        when(tokenService.generateToken(mockAuth)).thenReturn(mockToken);

        mockMvc.perform(post("/request-token")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(mockToken));
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthenticationFails() throws Exception {
        LoginRequest request = new LoginRequest("admin@admin.com", "wrongpass");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/request-token")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
} */