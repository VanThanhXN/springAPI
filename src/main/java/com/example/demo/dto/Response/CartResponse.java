package com.example.demo.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private List<CartItemResponse> items;
    private BigDecimal totalPrice;
    private int totalItems;
}