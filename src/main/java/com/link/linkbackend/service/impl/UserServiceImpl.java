package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.User;
import com.link.linkbackend.repository.UserRepository;
import com.link.linkbackend.service.UserService;
import com.link.linkbackend.service.dto.UserDTO;
import com.link.linkbackend.service.error.BadRequestException;
import com.link.linkbackend.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Service implementation for {@link UserService}.
 */
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO saveUser(User user) {
        return this.userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(User user) {
        // Check if user exists
        userRepository.findById(user.getId())
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "User not found"));
        return this.userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO partialUpdateUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // Partial update
            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPhone() != null) {
                existingUser.setPhone(user.getPhone());
            }
            return this.userMapper.toDto(userRepository.save(existingUser));
        } else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "User not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "User not found"));
        userRepository.delete(user);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDto);
    }

    @Override
    public UserDTO getUser(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "User not found"));
    }
}
