package com.rumbo.application.service;

import com.rumbo.application.port.in.LoginUserUseCase;
import com.rumbo.application.port.out.UserRepository;
import com.rumbo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserService implements LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginUserResult login(LoginUserCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        return new LoginUserResult(
                user.getId(),
                user.getEmail(),
                user.getNombre(),
                user.getApellidos()
        );
    }

    /**
     * Generic exception — never reveals whether email or password was wrong.
     * AC-02: "Email o contraseña incorrectos" sin indicar cuál falla.
     */
    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super("Email o contraseña incorrectos");
        }
    }
}
