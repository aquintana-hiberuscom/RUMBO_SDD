package com.rumbo.infrastructure.adapter.in.web;

public record RegisterResponse(
        Long userId,
        String email,
        String nombre,
        String apellidos,
        String token
) {}
