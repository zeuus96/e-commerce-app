package com.link.linkbackend.service.dto;

import com.link.linkbackend.domain.AbstractAuditingEntity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO extends AbstractAuditingEntity<String> {

    private long id;
    private String city;
    private String country;
    private String street;
    private String zipCode;
    private Double longitude;
    private Double latitude;
}
