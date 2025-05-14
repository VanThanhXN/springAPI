package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    private Long shippingId;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "shipping_method")
    private String shippingMethod; // GHTK, GHN, Viettel Post...

    @Column(name = "tracking_number")
    private String trackingNumber;

    private BigDecimal fee;

    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;
}