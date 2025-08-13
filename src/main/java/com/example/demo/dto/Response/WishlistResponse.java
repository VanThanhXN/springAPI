package com.example.demo.dto.Response;

import com.example.demo.dto.Response.ProductResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WishlistResponse {
    private Long wishlistId;
    private Long productId;
    private ProductResponse productDetails;
    private LocalDateTime addedAt;
}