package com.link.linkbackend.service.dto;


import com.link.linkbackend.domain.AbstractAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends AbstractAuditingEntity<String> {
    @Schema(description = "Order reference")
    private String ref;

    @NotBlank(message = "Order date is required.")
    @Schema(description = "Order date")
    private Instant date;

    @Schema(description = "Order status")
    private String status;

    @Schema(description = "Order description")
    private String description;

    @Schema(description = "Order rate")
    private int rate;

    @Schema(description = "Order purchased items")
    @NotBlank(message = "List of items is required.")
    private List<ItemDTO> items;

}
