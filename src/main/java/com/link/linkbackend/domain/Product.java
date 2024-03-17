package com.link.linkbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Product extends AbstractAuditingEntity<String> implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "rate")
    private int rate;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
    private Brand brand;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contact" }, allowSetters = true)
    private Shop shop;





}
