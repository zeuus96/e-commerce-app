package com.link.linkbackend.service;

import com.link.linkbackend.service.dto.BrandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    BrandDTO save(BrandDTO brandDTO);

    BrandDTO update(Long id, BrandDTO brandDTO);

    void delete(Long id);

    BrandDTO getBrandById(Long id);

    List<BrandDTO> getAllBrands();
    Page<BrandDTO> findByPage(Pageable pageable);
}
