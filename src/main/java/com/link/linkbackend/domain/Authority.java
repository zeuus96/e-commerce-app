package com.link.linkbackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "authority")
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

}
