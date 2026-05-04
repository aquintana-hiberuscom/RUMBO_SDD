package com.rumbo.infrastructure.adapter.in.web;

import com.rumbo.application.port.in.RegisterUserUseCase;
import com.rumbo.application.service.RegisterUserService.EmailAlreadyRegisteredException;
import com.rumbo.infrastructure.config.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;
    private final RegisterUserMapper registerUserMapper;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterUserUseCase.RegisterUserCommand command = new RegisterUserUseCase.RegisterUserCommand(
                request.email(),
                request.nombre(),
                request.apellidos(),
                request.edad(),
                request.password()
        );

        RegisterUserUseCase.RegisterUserResult result = registerUserUseCase.register(command);
        String token = jwtTokenProvider.generateToken(result.userId(), result.email());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(registerUserMapper.toResponse(result, token), "Usuario registrado correctamente"));
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyRegistered(
            EmailAlreadyRegisteredException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }
}
