package com.link.linkbackend.resource.controller;

import com.link.linkbackend.domain.User;
import com.link.linkbackend.service.UserService;
import com.link.linkbackend.service.dto.ResponseDTO;
import com.link.linkbackend.service.dto.UserDTO;
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

import java.util.List;

@RestController
@RequestMapping("/user")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
@Tag(name = "User Ref", description = "This is user Referential for all endpoints")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            })
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(@Parameter Pageable pageable) {
        log.debug("Fetching all users");
        Page<UserDTO> page = userService.getAllUsers(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @Operation(summary = "Get user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid username or user not found"),
          })
    @GetMapping("/find/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        log.debug("Fetching user by username: {}", username);
        UserDTO user = userService.getUser(username);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID or user not found"),
            })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        log.debug("Deleting user with ID: {}", id);
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (BadRequestException e) {
            log.error("Bad request exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user");
        }
    }

    @Operation(summary = "Save new user")
    @PostMapping("/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User saved successfully"),
           })

    public ResponseEntity<?> saveUser(@RequestBody User user) {
        log.debug("Saving new user: {}", user);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(),
                            "An error occurred while saving the user",
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID or user not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        log.debug("Updating user: {}", user);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @Operation(summary = "Partially update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID or user not found"),
           })
    @PatchMapping("/partial-update")
    public ResponseEntity<?> partialUpdateUser(@RequestBody User user) {
        log.debug("Partially updating user: {}", user);
        try {
            return ResponseEntity.ok(userService.partialUpdateUser(user));
        } catch (BadRequestException e) {
            log.error("Bad request exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(new ResponseDTO<>(e.getMessage(),
                            "Invalid user ID or user not found",
                            HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(),
                            "An error occurred while updating the user",
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
