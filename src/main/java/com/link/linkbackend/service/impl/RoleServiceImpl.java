package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.Role;
import com.link.linkbackend.repository.RoleRepository;
import com.link.linkbackend.service.RoleService;
import com.link.linkbackend.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * service implementation for {@link RoleService}.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role saveRole(Role role) {
        if (roleRepository.existsById(role.getName())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Role already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        Role existingRole = roleRepository.findById(role.getName())
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found"));
        existingRole.setDescription(role.getDescription());
        return roleRepository.save(existingRole);
    }

    @Override
    public Role getRole(String name) {
        return roleRepository.findById(name).orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found"));
    }

    @Override
    public void deleteRole(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found"));
        roleRepository.delete(role);

    }

}
