package com.link.linkbackend.service.dto;

import com.link.linkbackend.domain.AbstractAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends AbstractAuditingEntity<String> {

    @Schema(description = "The ID of the product.")
    private Long id;

    @NotBlank
    @Schema(description = "The name of the product.")
    private String name;

    @Schema(description = "The description of the product.")
    private String description;

    @Positive
    @Schema(description = "The price of the product.")
    private double price;

    @Positive
    @Schema(description = "The quantity of the product.")
    private int quantity;

    @Positive
    @Schema(description = "The rate of the product.")
    private int rate;

    @NotBlank
    @Schema(description = "The image URL of the product.")
    private String image;

    @NotBlank
    @Schema(description = "The status of the product.")
    private String status;

    @NotNull
    @Schema(description = "The ID of the brand associated with the product.")
    private Long brandId;

    @NotNull
    @Schema(description = "The ID of the shop associated with the product.")
    private Long shopId;

}
