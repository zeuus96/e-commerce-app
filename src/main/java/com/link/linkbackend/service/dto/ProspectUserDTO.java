package com.link.linkbackend.service.dto;

import com.link.linkbackend.domain.AbstractAuditingEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProspectUserDTO extends AbstractAuditingEntity<String> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long id;
    private String cin;
    private String email;
    private String firstName;
    private String lastName;
    private String patentNumber;
    private String phone;
    private String username;
    private AddressDTO address;

}
