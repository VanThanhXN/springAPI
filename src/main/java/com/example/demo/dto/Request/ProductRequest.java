package com.example.demo.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotNull(message = "Name cannot be null")
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal salePrice;
    private Long categoryId;
    private Integer stock;
    private Double rating;
    private MultipartFile image;
}
