package com.example.demo.dto.Response;

import com.example.demo.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusResponse {
    private Long orderId;
    private OrderStatus status;
    private String message;
}