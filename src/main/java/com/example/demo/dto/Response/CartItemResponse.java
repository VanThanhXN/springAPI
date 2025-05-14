package com.example.demo.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartItemResponse {
    private Long cartId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Integer quantity;
    private BigDecimal subTotal;
    private String addedAt;
}