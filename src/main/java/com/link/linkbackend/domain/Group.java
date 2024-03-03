package com.link.linkbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * A Group.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_group")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Group extends AbstractAuditingEntity<String> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "description")
    private String description;

}
