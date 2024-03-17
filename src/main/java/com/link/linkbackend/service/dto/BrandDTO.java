package com.link.linkbackend.service.dto;

import com.link.linkbackend.domain.AbstractAuditingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO extends AbstractAuditingEntity<String> {

    @Schema(description = "The unique identifier of the brand.")
    private Long id;

    @Schema(description = "The name of the brand.")
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "The description of the brand.")
    private String description;

    @Schema(description = "The URL of the brand's logo.")
    private String logo;

    @Schema(description = "The rate of the brand.")
    private Integer rate;
}
