// ReviewController.java
package com.example.demo.controller;

import com.example.demo.Repository.ReviewRepository;
import com.example.demo.dto.Request.ReviewRequest;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.ProductRatingResponse;
import com.example.demo.dto.Response.ReviewResponse;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProduct(@PathVariable Long productId) {
        List<ReviewResponse> reviews = reviewService.getReviewsByProduct(productId);
        return ApiResponse.<List<ReviewResponse>>builder()
                .result(reviews)
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<ReviewResponse> addReview(@RequestBody ReviewRequest request) {
        String username = getCurrentUsername();
        ReviewResponse response = reviewService.addReview(request, username);
        return ApiResponse.<ReviewResponse>builder()
                .result(response)
                .message("Đánh giá đã được thêm thành công")
                .build();
    }

    @PutMapping("/update/{reviewId}")
    public ApiResponse<ReviewResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest request) {
        String username = getCurrentUsername();
        ReviewResponse response = reviewService.updateReview(reviewId, request, username);
        return ApiResponse.<ReviewResponse>builder()
                .result(response)
                .message("Đánh giá đã được cập nhật thành công")
                .build();
    }

    @DeleteMapping("/delete/{reviewId}")
    public ApiResponse<String> deleteReview(@PathVariable Long reviewId) {
        String username = getCurrentUsername();
        reviewService.deleteReview(reviewId, username);
        return ApiResponse.<String>builder()
                .result("Đánh giá đã được xóa thành công")
                .build();
    }

    @GetMapping("/rating/{productId}")
    public ApiResponse<ProductRatingResponse> getProductRating(@PathVariable Long productId) {
        ProductRatingResponse response = reviewService.getProductRating(productId);
        return ApiResponse.<ProductRatingResponse>builder()
                .result(response)
                .build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    @GetMapping("/{productId}/review-count")
    public ApiResponse<Integer> getReviewCount(@PathVariable Long productId) {
        Integer count = reviewRepository.countReviewsByProductId(productId);
        return ApiResponse.<Integer>builder()
                .result(count)
                .build();
    }
    @GetMapping("/{productId}/reviews/stats")
    public ApiResponse<ProductRatingResponse> getReviewStats(@PathVariable Long productId) {
        return ApiResponse.<ProductRatingResponse>builder()
                .result(reviewService.getProductRating(productId))
                .build();
    }
}