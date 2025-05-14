package com.example.demo.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
    private Long cartId;
    private Long userId;
    private Long productId;
    private Integer quantity;
}
