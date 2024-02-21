package com.link.linkbackend.service.mapper;

import com.link.linkbackend.domain.User;
import com.link.linkbackend.service.dto.UserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper extends EntityMapper<UserDTO, User> {
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO userDto);
}
