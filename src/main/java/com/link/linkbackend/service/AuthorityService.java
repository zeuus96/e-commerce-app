package com.link.linkbackend.service;

import com.link.linkbackend.service.dto.AuthorityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AuthorityService {
    AuthorityDTO saveAuthority(AuthorityDTO authorityDTO);

    void deleteAuthority(String name);

    Page<AuthorityDTO> getAllAuthorities(Pageable pageable);
}
