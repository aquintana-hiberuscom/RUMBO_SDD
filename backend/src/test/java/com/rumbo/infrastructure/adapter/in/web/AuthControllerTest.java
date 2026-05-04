package com.rumbo.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumbo.application.port.in.RegisterUserUseCase;
import com.rumbo.application.service.RegisterUserService.EmailAlreadyRegisteredException;
import com.rumbo.infrastructure.config.JwtAuthenticationFilter;
import com.rumbo.infrastructure.config.JwtTokenProvider;
import com.rumbo.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class, GlobalExceptionHandler.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void register_withValidData_returns201WithToken() throws Exception {
        // Given
        var request = RegisterRequest.builder()
                .email("ana@example.com")
                .nombre("Ana")
                .apellidos("García López")
                .edad(28)
                .password("password123")
                .build();

        given(registerUserUseCase.register(any()))
                .willReturn(new RegisterUserUseCase.RegisterUserResult(1L, "ana@example.com", "Ana", "García López"));
        given(jwtTokenProvider.generateToken(1L, "ana@example.com")).willReturn("jwt.token.here");

        // When / Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("jwt.token.here"))
                .andExpect(jsonPath("$.data.email").value("ana@example.com"))
                .andExpect(jsonPath("$.message").value("Usuario registrado correctamente"));
    }

    @Test
    void register_withDuplicateEmail_returns409() throws Exception {
        // Given
        var request = RegisterRequest.builder()
                .email("existing@example.com")
                .nombre("Ana")
                .apellidos("García")
                .edad(25)
                .password("password123")
                .build();

        given(registerUserUseCase.register(any()))
                .willThrow(new EmailAlreadyRegisteredException("existing@example.com"));

        // When / Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("El email ya está registrado: existing@example.com"));
    }

    @Test
    void register_withInvalidEmail_returns400() throws Exception {
        // Given
        var request = RegisterRequest.builder()
                .email("not-an-email")
                .nombre("Ana")
                .apellidos("García")
                .edad(25)
                .password("password123")
                .build();

        // When / Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void register_withBlankName_returns400() throws Exception {
        // Given
        var request = RegisterRequest.builder()
                .email("test@example.com")
                .nombre("")
                .apellidos("García")
                .edad(25)
                .password("password123")
                .build();

        // When / Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
