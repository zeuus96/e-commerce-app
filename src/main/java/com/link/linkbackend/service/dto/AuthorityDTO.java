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
public class AuthorityDTO extends AbstractAuditingEntity<String> {
    private String name;
    private String description;


}
