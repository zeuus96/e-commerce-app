package com.link.linkbackend.service.mapper;

import com.link.linkbackend.domain.ProspectUser;
import com.link.linkbackend.service.dto.ProspectUserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProspectUserMapper extends EntityMapper<ProspectUserDTO, ProspectUser> {
}
