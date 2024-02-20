package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.ProspectUser;
import com.link.linkbackend.repository.ProspectUserRepository;
import com.link.linkbackend.service.ProspectUserService;
import com.link.linkbackend.service.dto.ProspectUserDTO;
import com.link.linkbackend.service.error.BadRequestException;
import com.link.linkbackend.service.mapper.ProspectUserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProspectUserServiceImpl implements ProspectUserService {
    private final ProspectUserMapper prospectUserMapper;
    private final ProspectUserRepository prospectUserRepository;

    public ProspectUserServiceImpl(ProspectUserMapper prospectUserMapper, ProspectUserRepository prospectUserRepository) {
        this.prospectUserMapper = prospectUserMapper;
        this.prospectUserRepository = prospectUserRepository;
    }

    @Override
    public ProspectUserDTO saveProspectUser(ProspectUserDTO prospectUserDTO) {
        Optional<ProspectUser> prospectUser = prospectUserRepository.findByEmail(prospectUserDTO.getEmail());
        if (prospectUser.isPresent()) {
            if (prospectUser.get().getEmail().equals(prospectUserDTO.getEmail())) {
                throw new BadRequestException("Email already in use");
            }
            if (prospectUser.get().getCin().equals(prospectUserDTO.getCin())) {
                throw new BadRequestException("CIN already in use");
            }
            if (prospectUser.get().getUsername().equals(prospectUserDTO.getUsername())) {
                throw new BadRequestException("Username already in use");
            }
            if (prospectUser.get().getPhone().equals(prospectUserDTO.getPhone())) {
                throw new BadRequestException("Phone number already in use");
            }
            if (prospectUser.get().getPatentNumber().equals(prospectUserDTO.getPatentNumber())) {
                throw new BadRequestException("Patent number already in use");
            }
        }
        return prospectUserMapper.toDto(prospectUserRepository.save(prospectUserMapper.toEntity(prospectUserDTO)));
    }

    @Override
    public Page<ProspectUserDTO> getAllProspectUsers(Pageable pageable) {
        return prospectUserRepository.findAll(pageable).map(prospectUserMapper::toDto);
    }


}
