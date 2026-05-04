package com.rumbo.application.service;

import com.rumbo.application.port.in.RegisterUserUseCase;
import com.rumbo.application.port.out.UserRepository;
import com.rumbo.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserService registerUserService;

    private RegisterUserUseCase.RegisterUserCommand validCommand;

    @BeforeEach
    void setUp() {
        validCommand = new RegisterUserUseCase.RegisterUserCommand(
                "test@example.com", "Ana", "García López", 28, "password123"
        );
    }

    @Test
    void register_withValidData_savesUserAndReturnsResult() {
        // Given
        given(userRepository.existsByEmail("test@example.com")).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("hashed_password");
        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            return user;
        });

        // When
        RegisterUserUseCase.RegisterUserResult result = registerUserService.register(validCommand);

        // Then
        assertThat(result.userId()).isNotNull();
        assertThat(result.email()).isEqualTo("test@example.com");
        assertThat(result.nombre()).isEqualTo("Ana");
        assertThat(result.apellidos()).isEqualTo("García López");
        then(userRepository).should().save(argThat(user ->
                user.getPasswordHash().equals("hashed_password") &&
                user.getEmail().equals("test@example.com")
        ));
    }

    @Test
    void register_withDuplicateEmail_throwsEmailAlreadyRegisteredException() {
        // Given
        given(userRepository.existsByEmail("test@example.com")).willReturn(true);

        // When / Then
        assertThatThrownBy(() -> registerUserService.register(validCommand))
                .isInstanceOf(RegisterUserService.EmailAlreadyRegisteredException.class)
                .hasMessageContaining("test@example.com");

        then(userRepository).should(never()).save(any());
    }

    @Test
    void register_passwordIsHashedBeforeSaving() {
        // Given
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("bcrypt_hash");
        given(userRepository.save(any(User.class))).willAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            return user;
        });

        // When
        registerUserService.register(validCommand);

        // Then — raw password never stored, hash used instead
        then(userRepository).should().save(argThat(user ->
                !user.getPasswordHash().equals("password123") &&
                user.getPasswordHash().equals("bcrypt_hash")
        ));
    }
}
