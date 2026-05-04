package com.rumbo.infrastructure.adapter.in.web;

import com.rumbo.application.port.in.RegisterUserUseCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Maps from the application result ({@link RegisterUserUseCase.RegisterUserResult}) to the web
 * response DTO ({@link RegisterResponse}). The {@code token} is generated in the controller
 * and passed as a separate parameter since it does not belong to the application layer.
 */
@Mapper(componentModel = "spring")
public interface RegisterUserMapper {

    @Mapping(source = "result.userId", target = "userId")
    @Mapping(source = "result.email", target = "email")
    @Mapping(source = "result.nombre", target = "nombre")
    @Mapping(source = "result.apellidos", target = "apellidos")
    @Mapping(source = "token", target = "token")
    RegisterResponse toResponse(RegisterUserUseCase.RegisterUserResult result, String token);
}
