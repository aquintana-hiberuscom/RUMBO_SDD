package com.rumbo.application.port.in;

import java.util.UUID;

/**
 * Inbound port — use case interface for user login.
 * Returns a JWT token on success. Throws InvalidCredentialsException on failure.
 */
public interface LoginUserUseCase {

    LoginUserResult login(LoginUserCommand command);

    record LoginUserCommand(
            String email,
            String password
    ) {}

    record LoginUserResult(
            UUID userId,
            String email,
            String nombre,
            String apellidos
    ) {}
}
