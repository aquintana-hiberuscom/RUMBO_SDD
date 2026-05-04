package com.rumbo.application.port.out;

import com.rumbo.domain.model.User;

import java.util.Optional;

/**
 * Outbound port — persistence contract.
 * The application layer depends on this interface, NOT on JPA.
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
