package com.rumbo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User domain entity.
 * NO Spring annotations. NO JPA annotations. Pure domain object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String email;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private String passwordHash;
}
