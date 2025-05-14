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
    private String categoryName;
    private Integer stock;
    private String imageUrls;
    private Double rating;
    private ProductStatus status;
}
