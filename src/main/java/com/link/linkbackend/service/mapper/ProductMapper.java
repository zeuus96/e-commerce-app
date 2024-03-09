package com.link.linkbackend.service.mapper;

import com.link.linkbackend.domain.Product;
import com.link.linkbackend.service.dto.ProductDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper extends EntityMapper<ProductDTO,Product> {
}
