package com.rumbo.application.service;

import com.rumbo.application.port.in.LoginUserUseCase;
import com.rumbo.application.port.out.UserRepository;
import com.rumbo.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginUserService loginUserService;

    @Test
    void login_withValidCredentials_returnsLoginResult() {
        // Given
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .email("ana@example.com")
                .nombre("Ana")
                .apellidos("García")
                .passwordHash("hashed_password")
                .build();

        given(userRepository.findByEmail("ana@example.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password123", "hashed_password")).willReturn(true);

        // When
        LoginUserUseCase.LoginUserResult result = loginUserService.login(
                new LoginUserUseCase.LoginUserCommand("ana@example.com", "password123")
        );

        // Then
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.email()).isEqualTo("ana@example.com");
        assertThat(result.nombre()).isEqualTo("Ana");
    }

    @Test
    void login_withUnknownEmail_throwsInvalidCredentialsException() {
        // Given — AC-02: error genérico, no revela si el email existe
        given(userRepository.findByEmail("unknown@example.com")).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> loginUserService.login(
                new LoginUserUseCase.LoginUserCommand("unknown@example.com", "password123")
        ))
                .isInstanceOf(LoginUserService.InvalidCredentialsException.class)
                .hasMessage("Email o contraseña incorrectos");
    }

    @Test
    void login_withWrongPassword_throwsInvalidCredentialsException() {
        // Given — AC-02: mismo mensaje que email incorrecto
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("ana@example.com")
                .passwordHash("hashed_password")
                .build();

        given(userRepository.findByEmail("ana@example.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongpassword", "hashed_password")).willReturn(false);

        // When / Then
        assertThatThrownBy(() -> loginUserService.login(
                new LoginUserUseCase.LoginUserCommand("ana@example.com", "wrongpassword")
        ))
                .isInstanceOf(LoginUserService.InvalidCredentialsException.class)
                .hasMessage("Email o contraseña incorrectos");
    }

    @Test
    void login_neverRevealsDifferentMessageForEmailVsPassword() {
        // Seguridad: el mensaje de error es IDÉNTICO para email no encontrado y contraseña incorrecta
        given(userRepository.findByEmail("noexiste@example.com")).willReturn(Optional.empty());

        Throwable exEmailUnknown = catchThrowable(() -> loginUserService.login(
                new LoginUserUseCase.LoginUserCommand("noexiste@example.com", "pass")
        ));

        User user = User.builder().id(UUID.randomUUID()).email("existe@example.com").passwordHash("hash").build();
        given(userRepository.findByEmail("existe@example.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("wrongpass", "hash")).willReturn(false);

        Throwable exWrongPassword = catchThrowable(() -> loginUserService.login(
                new LoginUserUseCase.LoginUserCommand("existe@example.com", "wrongpass")
        ));

        assertThat(exEmailUnknown).isInstanceOf(LoginUserService.InvalidCredentialsException.class);
        assertThat(exWrongPassword).isInstanceOf(LoginUserService.InvalidCredentialsException.class);
        assertThat(exEmailUnknown.getMessage()).isEqualTo(exWrongPassword.getMessage());
    }
}
