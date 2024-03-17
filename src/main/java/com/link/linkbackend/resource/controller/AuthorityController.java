package com.link.linkbackend.resource.controller;

import com.link.linkbackend.service.AuthorityService;
import com.link.linkbackend.service.dto.AuthorityDTO;
import com.link.linkbackend.service.dto.ResponseDTO;
import com.link.linkbackend.exception.BadRequestException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/authority")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
@Tag(name = "Authority Resource", description = "This is authority Referential for all endpoints")
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping("/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Authority created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid authority or authority already exists"),
            })
    public ResponseEntity<?> saveAuthority(
            @RequestBody AuthorityDTO authorityDTO
    ) {
        log.info("REST request to save Authority : {}", authorityDTO);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authorityService.saveAuthority(authorityDTO));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authority " + authorityDTO.getName() + " already exists");
        } catch (Exception e) {
            log.error("Internal server error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e, "Internal server error"));
        }
    }

    @DeleteMapping("/delete/{name}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authority deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid authority or authority not found"),
           })
    public ResponseEntity<?> deleteAuthority(
            @PathVariable String name
    ) {
        log.info("REST request to delete Authority : {}", name);
        try {
            authorityService.deleteAuthority(name);
            return ResponseEntity.status(HttpStatus.OK).body("Authority " + name + " deleted successfully");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authority " + name + " not found");
        } catch (Exception e) {
            log.error("Internal server error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e, "Internal server error"));
        }
    }

    @GetMapping("/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All authorities retrieved successfully"),
           })
    public ResponseEntity<?> getAllAuthorities(
            Pageable pageable) {
        log.info("REST request to get all Authorities");
        try {
            Page<AuthorityDTO> authorities = authorityService.getAllAuthorities(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(authorities);
        } catch (Exception e) {
            log.error("Internal server error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e, "Internal server error while retrieving authorities"));
        }
    }
}
