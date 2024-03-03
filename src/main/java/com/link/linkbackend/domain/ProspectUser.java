package com.link.linkbackend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "prospect_user")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProspectUser extends AbstractAuditingEntity<String> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private long id;

    @Column(name = "cin", length = 13, unique = true)
    private String cin;

    @Column(length = 254, unique = true, name = "email")
    private String email;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 20, name = "patent_number", unique = true)
    private String patentNumber;

    @Column(length = 13, unique = true, name = "phone")
    private String phone;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 100)
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
