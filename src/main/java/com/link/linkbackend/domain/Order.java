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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Order extends AbstractAuditingEntity<String> implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ref")
    private String ref;

    @Column(name = "date")
    private Instant date;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "rate")
    private int rate;

    @OneToMany(mappedBy = "order")
    @org.springframework.data.annotation.Transient
    @JsonIgnoreProperties(value = {"brand", "shop", "order"}, allowSetters = true)
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private DeliverySchedule deliverySchedule;

    @ManyToOne
    private User user;

    double getSum(){
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += item.getPrice();
        }
        return sum;
    }

}
