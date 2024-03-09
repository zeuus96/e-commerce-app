package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.Product;
import com.link.linkbackend.repository.ProductRepository;
import com.link.linkbackend.service.ProductService;
import com.link.linkbackend.service.dto.ProductDTO;
import com.link.linkbackend.service.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductDTO> findByPage(Pageable pageable) {
        log.info("Recovering list of products page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    public Product save(ProductDTO productDTO) {
        log.info("Creating a new product");
        return productRepository.save(productMapper.toEntity(productDTO));
    }
}
