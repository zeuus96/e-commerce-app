package com.link.linkbackend.resource.controller;

import com.link.linkbackend.domain.Role;
import com.link.linkbackend.service.RoleService;
import com.link.linkbackend.service.dto.ResponseDTO;
import com.link.linkbackend.service.error.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/role")
@Tag(name = "Role Resource", description = "This is role Referential for all endpoints")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
@Slf4j
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Get all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles fetched successfully"),
            })
    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles(
            @RequestParam Pageable pageable
    ) {
        log.debug("REST request to get all Roles with pageable: {}", pageable);
        Page<Role> page = roleService.getAllRoles(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @Operation(summary = "Save new role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role name or role already exists"),
           })
    @PostMapping("/save")
    public ResponseEntity<?> saveRole(
            @RequestBody Role role
    ) {
        log.debug("REST request to save Role : {}", role);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(role));
        } catch (BadRequestException e) {
            log.error("Bad request exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while saving the role",
                    HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while saving the role",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Operation(summary = "Update role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role name or role not found"),
            })
    @PutMapping("/update")
    public ResponseEntity<?> updateRole(
            @RequestBody Role role
    ) {
        log.debug("REST request to update Role : {}", role);
        try {
            return ResponseEntity.ok(roleService.updateRole(role));
        } catch (BadRequestException e) {
            log.error("Bad request exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while updating the role",
                    HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while updating the role",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }


    @Operation(summary = "Get role by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role name or role not found"),
           })
    @GetMapping("/get/{name}")
    public ResponseEntity<?> getRole(
            @PathVariable String name
    ) {
        log.debug("REST request to get Role : {}", name);
        try {
            Role role = roleService.getRole(name);
            return ResponseEntity.ok(role);
        } catch (BadRequestException e) {
            log.error("Bad request exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while fetching the role",
                    HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while fetching the role",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Operation(summary = "Delete role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role name or role not found"),
           })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRole(
            @PathVariable String name
    ) {
        log.debug("REST request to delete Role : {}", name);
        try {
            roleService.deleteRole(name);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            log.error("Bad request exception: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while deleting the role",
                    HttpStatus.BAD_REQUEST.value()));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(e.getMessage(),
                    "An error occurred while deleting the role",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}