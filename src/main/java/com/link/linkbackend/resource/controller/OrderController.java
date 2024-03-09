package com.link.linkbackend.resource.controller;

import com.link.linkbackend.service.OrderService;
import com.link.linkbackend.service.dto.OrderDTO;
import com.link.linkbackend.service.dto.ResponseDTO;
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
@RequestMapping("/order")
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")})
@Tag(name = "Order Resource", description = "This is Order Referential for all endpoints")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Place a new Order")
    @PostMapping("/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order saved successfully"),
    })
    public ResponseEntity<?> saveOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("REST request to place a new order: {}", orderDTO);

        try {
            orderService.placeOrder(orderDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO<>(e.getMessage(),
                            "An error occurred while saving the order",
                            HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Operation(summary = "List Orders")
    @GetMapping("/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of orders")
    })
    public ResponseEntity<List<OrderDTO>> all() {
        log.debug("REST request to get all Orders");
        var orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }


    @Operation(summary = "List Orders by page")
    @GetMapping("/by-page")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of orders")
    })
    public ResponseEntity<List<OrderDTO>> getOrdersPage(@ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of orders");
        Page<OrderDTO> page = orderService.findByPage(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
