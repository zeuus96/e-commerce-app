package com.link.linkbackend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Role extends AbstractAuditingEntity<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "name"))
    private Set<Authority> authorities;
}
