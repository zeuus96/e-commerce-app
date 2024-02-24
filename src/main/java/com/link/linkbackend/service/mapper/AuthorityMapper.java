package com.link.linkbackend.service.mapper;

import com.link.linkbackend.domain.Authority;
import com.link.linkbackend.service.dto.AuthorityDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {
    Authority toEntity(AuthorityDTO authorityDTO);
}
