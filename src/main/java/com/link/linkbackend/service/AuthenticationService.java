package com.link.linkbackend.service;


import com.link.linkbackend.resource.request.AuthRequest;
import com.link.linkbackend.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService extends UserDetailsService {
    String signin(AuthRequest request);
    void signup(UserDTO userDto);
}