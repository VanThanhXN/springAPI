package com.example.demo.dto.Response;


import com.example.demo.entity.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Integer stock;
    private String imageUrl;
    private Double rating;
    private ProductStatus status;
    private String categoryName;
}
