package com.rumbo.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private Integer edad;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
