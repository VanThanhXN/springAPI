// ReviewService.java
package com.example.demo.service;

import com.example.demo.dto.Request.ReviewRequest;
import com.example.demo.dto.Response.ProductRatingResponse;
import com.example.demo.dto.Response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    List<ReviewResponse> getReviewsByProduct(Long productId);
    ReviewResponse addReview(ReviewRequest request, String username);
    ReviewResponse updateReview(Long reviewId, ReviewRequest request, String username);
    void deleteReview(Long reviewId, String username);
    ProductRatingResponse getProductRating(Long productId);
}