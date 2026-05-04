package com.rumbo.infrastructure.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumbo.application.port.in.LoginUserUseCase;
import com.rumbo.application.port.in.RegisterUserUseCase;
import com.rumbo.application.service.LoginUserService.InvalidCredentialsException;
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

import java.util.UUID;

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
    private LoginUserUseCase loginUserUseCase;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private RegisterUserMapper registerUserMapper;

    @MockBean
    private LoginUserMapper loginUserMapper;

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

        UUID userId = UUID.randomUUID();
        given(registerUserUseCase.register(any()))
                .willReturn(new RegisterUserUseCase.RegisterUserResult(userId, "ana@example.com", "Ana", "García López"));
        given(jwtTokenProvider.generateToken(userId, "ana@example.com")).willReturn("jwt.token.here");
        given(registerUserMapper.toResponse(any(), any()))
                .willReturn(new RegisterResponse(userId, "ana@example.com", "Ana", "García López", "jwt.token.here"));

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

    // ─── Login tests ──────────────────────────────────────────────────────────

    @Test
    void login_withValidCredentials_returns200WithToken() throws Exception {
        // Given
        var request = LoginRequest.builder()
                .email("ana@example.com")
                .password("password123")
                .build();

        UUID userId = UUID.randomUUID();
        given(loginUserUseCase.login(any()))
                .willReturn(new LoginUserUseCase.LoginUserResult(userId, "ana@example.com", "Ana", "García"));
        given(jwtTokenProvider.generateToken(userId, "ana@example.com")).willReturn("jwt.token.here");
        given(loginUserMapper.toResponse(any(), any()))
                .willReturn(new LoginResponse(userId, "ana@example.com", "Ana", "García", "jwt.token.here"));

        // When / Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").value("jwt.token.here"))
                .andExpect(jsonPath("$.data.email").value("ana@example.com"))
                .andExpect(jsonPath("$.message").value("Login correcto"));
    }

    @Test
    void login_withInvalidCredentials_returns401() throws Exception {
        // Given
        var request = LoginRequest.builder()
                .email("ana@example.com")
                .password("wrongpassword")
                .build();

        given(loginUserUseCase.login(any()))
                .willThrow(new InvalidCredentialsException());

        // When / Then — AC-02: mensaje genérico, no revela qué campo falla
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email o contraseña incorrectos"));
    }

    @Test
    void login_withInvalidEmailFormat_returns400() throws Exception {
        // Given
        var request = LoginRequest.builder()
                .email("not-an-email")
                .password("password123")
                .build();

        // When / Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
