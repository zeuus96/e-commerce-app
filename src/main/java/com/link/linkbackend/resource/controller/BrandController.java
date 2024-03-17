package com.link.linkbackend.resource.controller;

import com.link.linkbackend.service.BrandService;
import com.link.linkbackend.service.dto.BrandDTO;
import com.link.linkbackend.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/brand")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
@Tag(name = "Brand Resource", description = "This is Brand Referential for all endpoints")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = " Successfully created brand")
    })
    @Operation(summary = "Create a new brand")
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandDTO brand) {
        log.debug("REST request to create a Brand : {}", brand);
        if (brand.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A new brand cannot already have an ID");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brand));

    }

    @Operation(summary = "List brands")
    @GetMapping("/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of brands")
    })
    public ResponseEntity<List<BrandDTO>> all() {
        log.debug("REST request to get all brands");
        var brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }


    @Operation(summary = "List brands by page")
    @GetMapping("/by-page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of brands")
    })
    public ResponseEntity<List<BrandDTO>> getBrandPage(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of brands");
        Page<BrandDTO> page = brandService.findByPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
    })
    @Operation(summary = "Update an existing brand by ID")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandDTO brandDTO) {
        log.info("Updating brand with ID {}: {}", id, brandDTO);
        BrandDTO updatedBrand = brandService.update(id, brandDTO);
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
    })
    @Operation(summary = "Delete a brand by ID")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        log.info("Deleting brand with ID {}", id);
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand fetched successfully"),
    })
    @Operation(summary = "Get a brand by ID")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        log.info("Fetching brand with ID {}", id);
        BrandDTO brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

}

