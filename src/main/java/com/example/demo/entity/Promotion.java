package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(unique = true, nullable = false)
    private String code; // Ví dụ: "SALE20"

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue; // 20% hoặc 50.000 VND

    @Column(name = "min_order")
    private BigDecimal minOrder; // Đơn tối thiểu

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_active")
    private Boolean isActive;
}