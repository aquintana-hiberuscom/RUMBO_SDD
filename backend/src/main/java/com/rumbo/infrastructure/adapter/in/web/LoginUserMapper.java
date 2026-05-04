package com.rumbo.infrastructure.adapter.in.web;

import com.rumbo.application.port.in.LoginUserUseCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginUserMapper {

    @Mapping(source = "result.userId", target = "userId")
    @Mapping(source = "result.email", target = "email")
    @Mapping(source = "result.nombre", target = "nombre")
    @Mapping(source = "result.apellidos", target = "apellidos")
    @Mapping(source = "token", target = "token")
    LoginResponse toResponse(LoginUserUseCase.LoginUserResult result, String token);
}
