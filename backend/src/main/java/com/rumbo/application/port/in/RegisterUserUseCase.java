package com.rumbo.application.port.in;

/**
 * Inbound port — use case interface for user registration.
 * Called by web adapters. Does NOT depend on infrastructure.
 */
public interface RegisterUserUseCase {

    RegisterUserResult register(RegisterUserCommand command);

    record RegisterUserCommand(
            String email,
            String nombre,
            String apellidos,
            Integer edad,
            String password
    ) {}

    record RegisterUserResult(
            java.util.UUID userId,
            String email,
            String nombre,
            String apellidos
    ) {}
}
