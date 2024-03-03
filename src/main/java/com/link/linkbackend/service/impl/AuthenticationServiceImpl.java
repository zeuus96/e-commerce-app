package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.Role;
import com.link.linkbackend.domain.User;
import com.link.linkbackend.repository.RoleRepository;
import com.link.linkbackend.repository.UserRepository;
import com.link.linkbackend.resource.request.AuthRequest;
import com.link.linkbackend.service.AuthenticationService;
import com.link.linkbackend.service.JwtService;
import com.link.linkbackend.service.dto.UserDTO;
import com.link.linkbackend.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByUsername(username);
        return userDetail
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    @Override
    public String signin(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
       return jwtService.generateToken(user);

    }

    @Override
    public void signup(UserDTO userDto) {
        if ( userRepository.existsByUsername(userDto.getUsername()) ){
            throw new IllegalArgumentException("User already Exists");
        }else {
            var user = userMapper.toEntity(userDto).setPassword(passwordEncoder.encode(userDto.getPassword()));
            if (!CollectionUtils.isEmpty(userDto.getRoleList())){
                Set<Role> userRoles = new HashSet<>();
                for (String roleName : userDto.getRoleList()) {
                    roleRepository.findById(roleName).ifPresentOrElse(roleFound ->{
                        userRoles.add(roleFound);
                    },() -> {
                        log.error("Added role = {} does not exist", roleName);
                    });
                }
            }
            userRepository.save(user);
            log.info("User saved succesfully");
        }

    }
}

