package com.link.linkbackend.service.dto;

import com.link.linkbackend.domain.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO extends AbstractAuditingEntity<String> {

    private long id;
    private String city;
    private String country;
    private String street;
    private String zipCode;
    private Double longitude;
    private Double latitude;
}
