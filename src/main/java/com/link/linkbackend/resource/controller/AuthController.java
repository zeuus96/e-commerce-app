package com.link.linkbackend.resource.controller;

import com.link.linkbackend.resource.request.AuthRequest;
import com.link.linkbackend.service.AuthenticationService;
import com.link.linkbackend.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication referential")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
public class AuthController {
    private final AuthenticationService authenticationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in")})
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully")})
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody UserDTO userDto) {
        log.info("REST request to save User");
        authenticationService.signup(userDto);
        return  new ResponseEntity<>(userDto,HttpStatus.CREATED);
    }
}
