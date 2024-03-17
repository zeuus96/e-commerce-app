package com.link.linkbackend.service.mapper;

import com.link.linkbackend.domain.Brand;
import com.link.linkbackend.service.dto.BrandDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BrandMapper extends EntityMapper<BrandDTO, Brand>{
}
