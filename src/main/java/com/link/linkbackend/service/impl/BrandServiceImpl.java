package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.Brand;
import com.link.linkbackend.exception.ObjectNotFoundException;
import com.link.linkbackend.repository.BrandRepository;
import com.link.linkbackend.service.BrandService;
import com.link.linkbackend.service.dto.BrandDTO;
import com.link.linkbackend.service.mapper.BrandMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {
    private final BrandMapper brandMapper;
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandMapper brandMapper, BrandRepository brandRepository) {
        this.brandMapper = brandMapper;
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        log.info("Creating a new brand: {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toDto(savedBrand);
    }

    @Override
    public BrandDTO update(Long id, BrandDTO brandDTO) {
        log.info("Updating brand with ID {}: {}", id, brandDTO);
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()) {
            Brand existingBrand = optionalBrand.get();
            Brand updatedBrand = brandRepository.save(existingBrand);
            return brandMapper.toDto(updatedBrand);
        } else {
            log.error("Brand with ID {} not found", id);
            throw new ObjectNotFoundException("Brand not found");
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting brand with ID = {}", id);
        if (brandRepository.existsById(id)) {
            brandRepository.deleteById(id);
        } else {
            log.error("Brand with ID = {} not found", id);
            throw new ObjectNotFoundException("Brand not found");
        }
    }

    @Override
    public BrandDTO getBrandById(Long id) {
        log.info("Fetching brand with ID {}", id);
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        return optionalBrand.map(brandMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Brand with ID {} not found", id);
                    return new ObjectNotFoundException("Brand not found");
                });
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        log.info("Fetching all brands");
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brandMapper::toDto)
                .toList();
    }

    @Override
    public Page<BrandDTO> findByPage(Pageable pageable) {
        log.info("Recovering list of orders page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return brandRepository.findAll(pageable).map(brandMapper::toDto);
    }
}
