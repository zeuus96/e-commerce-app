package com.link.linkbackend.service.mapper;

import com.link.linkbackend.domain.ProspectUser;
import com.link.linkbackend.service.dto.ProspectUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProspectUserMapper extends EntityMapper<ProspectUserDTO, ProspectUser> {
}
