package com.link.linkbackend.service.dto;

import com.link.linkbackend.domain.AbstractAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO extends AbstractAuditingEntity<String> {

    @Positive
    @Schema(description = "The quantity of the order item.")
    private int quantity;

    @Schema(description = "The description of the order item.")
    private String description;

    @NotNull
    @Schema(description = "The product associated with the order item.")
    private Long productId;

}
