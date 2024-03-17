package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.Authority;
import com.link.linkbackend.repository.AuthorityRepository;
import com.link.linkbackend.service.AuthorityService;
import com.link.linkbackend.service.dto.AuthorityDTO;
import com.link.linkbackend.exception.BadRequestException;
import com.link.linkbackend.service.mapper.AuthorityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    @Override
    public AuthorityDTO saveAuthority(AuthorityDTO authorityDTO) {
        Optional<Authority> authority = authorityRepository.findByName(authorityDTO.getName());
        if (authority.isPresent()) {
            log.error("Authority {} already exists", authorityDTO.getName());
            throw new BadRequestException("Authority already exists");
        }
        return authorityMapper.toDto(authorityRepository.save(authorityMapper.toEntity(authorityDTO)));
    }

    @Override
    public void deleteAuthority(String name) {
        Optional<Authority> authority = authorityRepository.findByName(name);
        if (authority.isEmpty()) {
            log.error("Authority {} not found", name);
            throw new BadRequestException("Authority not found");
        }
        authorityRepository.delete(authority.get());
    }

    @Override
    public Page<AuthorityDTO> getAllAuthorities(Pageable pageable) {
        return authorityRepository.findAll(pageable).map(authorityMapper::toDto);
    }
}
