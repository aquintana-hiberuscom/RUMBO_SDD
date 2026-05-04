package com.rumbo.infrastructure.adapter.in.web;

import java.util.UUID;

public record LoginResponse(
        UUID userId,
        String email,
        String nombre,
        String apellidos,
        String token
) {}
