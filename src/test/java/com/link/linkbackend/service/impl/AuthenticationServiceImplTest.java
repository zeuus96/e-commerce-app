package com.link.linkbackend.service.impl;

import com.link.linkbackend.domain.User;
import com.link.linkbackend.repository.RoleRepository;
import com.link.linkbackend.repository.UserRepository;
import com.link.linkbackend.service.JwtService;
import com.link.linkbackend.service.dto.UserDTO;
import com.link.linkbackend.service.mapper.UserMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceImplTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    private final EasyRandom easyRandom = new EasyRandom();


    /**
     * Method under test: {@link AuthenticationServiceImpl#signup(UserDTO)}
     */
    @Test
    void testSignup() {
        User user = easyRandom.nextObject(User.class);
        UserDTO userDto = easyRandom.nextObject(UserDTO.class);
        when(userMapper.toEntity(Mockito.<UserDTO>any())).thenReturn(user);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        authenticationServiceImpl.signup(userDto);
        verify(userMapper).toEntity(Mockito.<UserDTO>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#signup(UserDTO)}
     */
    @Test
    void testSignupShouldThrowException() {
        UserDTO userDto = easyRandom.nextObject(UserDTO.class);
        when(userRepository.existsByUsername(any())).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class, () -> authenticationServiceImpl.signup(userDto));
        verify(userMapper, never()).toEntity(Mockito.<UserDTO>any());
        verify(userRepository, never()).save(Mockito.<User>any());
        verify(passwordEncoder, never()).encode(Mockito.<CharSequence>any());
    }
}

