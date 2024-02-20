package com.link.linkbackend.service;

import com.link.linkbackend.service.dto.ProspectUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProspectUserService {
    ProspectUserDTO saveProspectUser(ProspectUserDTO prospectUserDTO);

    Page<ProspectUserDTO> getAllProspectUsers(Pageable pageable);
}
