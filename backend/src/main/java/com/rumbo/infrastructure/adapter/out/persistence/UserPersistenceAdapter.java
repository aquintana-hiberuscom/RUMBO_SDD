package com.rumbo.infrastructure.adapter.out.persistence;

import com.rumbo.application.port.out.UserRepository;
import com.rumbo.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {
        UserJpaEntity entity = toEntity(user);
        UserJpaEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nombre(user.getNombre())
                .apellidos(user.getApellidos())
                .edad(user.getEdad())
                .passwordHash(user.getPasswordHash())
                .build();
    }

    private User toDomain(UserJpaEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .nombre(entity.getNombre())
                .apellidos(entity.getApellidos())
                .edad(entity.getEdad())
                .passwordHash(entity.getPasswordHash())
                .build();
    }
}
