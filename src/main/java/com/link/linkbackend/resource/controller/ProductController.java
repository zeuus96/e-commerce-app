package com.link.linkbackend.resource.controller;

import com.link.linkbackend.service.ProductService;
import com.link.linkbackend.service.dto.ProductDTO;
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
@RequestMapping("/product")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
@Tag(name = "Product Resource", description = "This is Product Referential for all endpoints")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/by-page")
    @Operation(summary = "List products by page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of products")
    })
    public ResponseEntity<List<ProductDTO>> getProductsPage(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Products");
        Page<ProductDTO> page = productService.findByPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @PostMapping("/create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = " Successfully created product")
    })
    @Operation(summary = "Create a new product")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO product){
        log.debug("REST request to create a Product : {}", product);
        if (product.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A new product cannot already have an ID");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));

    }

}
