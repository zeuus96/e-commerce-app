package com.link.linkbackend.service;

import com.link.linkbackend.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Interface for managing {@link Role}.
 */
@Service
public interface RoleService {
    Page<Role> getAllRoles(Pageable pageable);

    Role saveRole(Role role);

    Role updateRole(Role role);

    Role getRole(String id);

    void deleteRole(String id);
}
