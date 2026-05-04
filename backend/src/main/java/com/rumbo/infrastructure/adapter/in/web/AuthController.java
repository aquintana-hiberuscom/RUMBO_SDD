package com.rumbo.infrastructure.adapter.in.web;

import com.rumbo.application.port.in.LoginUserUseCase;
import com.rumbo.application.port.in.RegisterUserUseCase;
import com.rumbo.application.service.LoginUserService.InvalidCredentialsException;
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
    private final LoginUserUseCase loginUserUseCase;
    private final JwtTokenProvider jwtTokenProvider;
    private final RegisterUserMapper registerUserMapper;
    private final LoginUserMapper loginUserMapper;

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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginUserUseCase.LoginUserResult result = loginUserUseCase.login(
                new LoginUserUseCase.LoginUserCommand(request.email(), request.password())
        );

        String token = jwtTokenProvider.generateToken(result.userId(), result.email());

        return ResponseEntity.ok(
                ApiResponse.ok(loginUserMapper.toResponse(result, token), "Login correcto")
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(ex.getMessage()));
    }
}
