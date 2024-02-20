package com.link.linkbackend.service;

import com.link.linkbackend.domain.User;
import com.link.linkbackend.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Interface for managing {@link User}.
 */
@Service
public interface UserService {

    UserDTO saveUser(User user);

    UserDTO updateUser(User user);

    UserDTO partialUpdateUser(User user);


    void deleteUser(Long id);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO getUser(String username);
}
