package com.example.demo.dto.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CartRequest {
    private UUID userId; // ✅ Đúng kiểu
    private Long productId;
    private Integer quantity;
}
