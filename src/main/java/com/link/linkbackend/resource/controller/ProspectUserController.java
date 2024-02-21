package com.link.linkbackend.resource.controller;

import com.link.linkbackend.service.ProspectUserService;
import com.link.linkbackend.service.dto.ProspectUserDTO;
import com.link.linkbackend.service.dto.ResponseDTO;
import com.link.linkbackend.service.error.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prospect-user")
@Tag(name = "Prospect User Resource", description = "This is prospect user referential for all endpoints")
@Slf4j
public class ProspectUserController {
    private final ProspectUserService prospectUserService;

    public ProspectUserController(ProspectUserService prospectUserService) {
        this.prospectUserService = prospectUserService;
    }

    @PostMapping("/save")
    @Tag(name = "Save Prospect User", description = "This is the endpoint to save a new prospect user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prospect user created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid prospect user or prospect user already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<?> saveProspectUser(
            @RequestBody ProspectUserDTO prospectUserDTO
    ) {
        log.debug("REST request to save Prospect User");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(prospectUserService.saveProspectUser(prospectUserDTO));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), "Invalid prospect user"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(), "An error occurred while saving prospect user"));
        }
    }

    @GetMapping("/all")
    @Operation(description = "This is the endpoint to get all prospect users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prospect users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<?> getAllProspectUsers(@Parameter Pageable pageable) {
        log.debug("REST request to get all Prospect Users {}", pageable);
        try {
            Page<ProspectUserDTO> page = prospectUserService.getAllProspectUsers(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(), "An error occurred while retrieving prospect users"));
        }
    }


}
