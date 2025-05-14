package com.example.demo.dto.Request;

import com.example.demo.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Shipping address cannot be null")
    private String shippingAddress;
}