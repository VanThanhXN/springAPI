package com.example.demo.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRequest {
    @NotNull(message = "Name cannot be null")
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Long categoryId;
    private Integer stock;
    private MultipartFile image;
}
