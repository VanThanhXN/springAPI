package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  // Giữ nguyên để có thể truy xuất thông tin category

    private Integer stock;

    @Column(name = "image_urls", columnDefinition = "JSON")
    private String imageUrls;


    private Double rating;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // Enum trạng thái

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
