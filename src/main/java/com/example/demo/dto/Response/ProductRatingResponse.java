// ProductRatingResponse.java
package com.example.demo.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRatingResponse {
    private Long productId;
    private String productName;
    private Double averageRating;
    private Integer totalReviews;
}