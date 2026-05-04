package com.rumbo.application.service;

import com.rumbo.application.port.in.RegisterUserUseCase;
import com.rumbo.application.port.out.UserRepository;
import com.rumbo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResult register(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyRegisteredException(command.email());
        }

        User user = User.builder()
                .email(command.email())
                .nombre(command.nombre())
                .apellidos(command.apellidos())
                .edad(command.edad())
                .passwordHash(passwordEncoder.encode(command.password()))
                .build();

        User saved = userRepository.save(user);

        return new RegisterUserResult(
                saved.getId(),
                saved.getEmail(),
                saved.getNombre(),
                saved.getApellidos()
        );
    }

    public static class EmailAlreadyRegisteredException extends RuntimeException {
        public EmailAlreadyRegisteredException(String email) {
            super("El email ya está registrado: " + email);
        }
    }
}
