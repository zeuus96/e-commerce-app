package com.link.linkbackend.service;

import com.link.linkbackend.domain.Product;
import com.link.linkbackend.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Page<ProductDTO> findByPage(Pageable pageable);

    Product save(ProductDTO product);
}
