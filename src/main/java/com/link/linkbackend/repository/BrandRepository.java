package com.link.linkbackend.repository;

import com.link.linkbackend.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Brand entity.
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {}
