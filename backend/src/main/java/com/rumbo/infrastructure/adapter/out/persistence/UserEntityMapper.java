package com.rumbo.infrastructure.adapter.out.persistence;

import com.rumbo.domain.model.User;
import org.mapstruct.Mapper;

/**
 * Maps between the domain model ({@link User}) and the JPA entity ({@link UserJpaEntity}).
 * All field names are identical so no explicit @Mapping is needed.
 */
@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserJpaEntity toEntity(User user);

    User toDomain(UserJpaEntity entity);
}
