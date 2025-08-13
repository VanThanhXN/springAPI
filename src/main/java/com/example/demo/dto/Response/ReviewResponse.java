// ReviewResponse.java
package com.example.demo.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long reviewId;
    private Long productId;
    private Long userId;
    private String username;
    private String userAvatar;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}