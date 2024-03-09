package com.link.linkbackend.service;

import com.link.linkbackend.domain.ProspectUser;
import com.link.linkbackend.repository.ProspectUserRepository;
import com.link.linkbackend.service.dto.ProspectUserDTO;
import com.link.linkbackend.service.error.BadRequestException;
import com.link.linkbackend.service.impl.ProspectUserServiceImpl;
import com.link.linkbackend.service.mapper.ProspectUserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
 class ProspectUserServiceTest {

    @Mock
    private ProspectUserRepository prospectUserRepository;

    @Mock
    private ProspectUserMapper prospectUserMapper; // Mock the mapper

    @InjectMocks
    private ProspectUserServiceImpl prospectUserServiceImpl;

    @Test
     void saveProspectUser_EmailAlreadyExists_ThrowsBadRequestException() {
        // Arrange
        ProspectUserDTO prospectUserDTO = new ProspectUserDTO();
        prospectUserDTO.setEmail("existing@example.com");

        ProspectUser existingUser = new ProspectUser();
        existingUser.setId(1L);
        existingUser.setEmail(prospectUserDTO.getEmail());

        when(prospectUserRepository.findByEmail(prospectUserDTO.getEmail())).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThatThrownBy(() -> prospectUserServiceImpl.saveProspectUser(prospectUserDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email already in use");
    }

    @Test
     void saveProspectUser_CinAlreadyExists_ThrowsBadRequestException() {
        // Arrange
        ProspectUserDTO prospectUserDTO = ProspectUserDTO.builder()
                .email("newEmail").cin("75607697")
                .patentNumber("newPatentNumber").phone("newPhone").build();
        ProspectUser existingUser = ProspectUser.builder()
                .email("newEmail@gmail").cin("75607697").username("help")
                .patentNumber("776678").phone("767)").build();

        when(prospectUserRepository.findByEmail(prospectUserDTO.getEmail())).thenReturn(Optional.of(existingUser));
        // Act & Assert
        assertThatThrownBy(() -> prospectUserServiceImpl.saveProspectUser(prospectUserDTO))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("CIN already in use");
    }

    @Test
     void saveProspectUser_ReturnsProspectUserDTO() {
        // Arrange
        ProspectUserDTO prospectUserDTO = ProspectUserDTO.builder()
                .email("newEmail").cin("77676777777").username("newUsername")
                .patentNumber("newPatentNumber").phone("newPhone").build();
        ProspectUser newUser = ProspectUser.builder()
                .email("newEmail").cin("77676777777").username("newUsername")
                .patentNumber("newPatentNumber").phone("newPhone").build();
        when(prospectUserRepository.findByEmail(prospectUserDTO.getEmail())).thenReturn(Optional.empty());
        when(prospectUserMapper.toEntity(prospectUserDTO)).thenReturn(newUser); // Mocking mapper to return existingUser
        when(prospectUserRepository.save(newUser)).thenReturn(newUser);
        when(prospectUserMapper.toDto(newUser)).thenReturn(prospectUserDTO);
        // Act
        ProspectUserDTO result = prospectUserServiceImpl.saveProspectUser(prospectUserDTO);

        // Assert that the result is not null && is equal to the prospectUserDTO
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(prospectUserDTO);
    }


}
