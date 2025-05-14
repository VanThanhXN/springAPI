package com.example.demo.dto.Response;

import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private String shippingAddress;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponse> items;
}