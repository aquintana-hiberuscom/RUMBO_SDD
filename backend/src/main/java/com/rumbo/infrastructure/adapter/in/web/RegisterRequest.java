package com.rumbo.infrastructure.adapter.in.web;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "Los apellidos son obligatorios")
        String apellidos,

        @NotNull(message = "La edad es obligatoria")
        @Min(value = 1, message = "La edad debe ser un número positivo")
        @Max(value = 120, message = "La edad no es válida")
        Integer edad,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password
) {}
