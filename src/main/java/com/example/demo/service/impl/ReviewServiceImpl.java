// ReviewServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.Request.ReviewRequest;
import com.example.demo.dto.Response.ProductRatingResponse;
import com.example.demo.dto.Response.ReviewResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;


    @Override
    public List<ReviewResponse> getReviewsByProduct(Long productId) {
        List<Review> reviews = reviewRepository.findByProductProductId(productId);
        return reviews.stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse addReview(ReviewRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (reviewRepository.existsByUserUserIdAndProductProductId(user.getUserId(), product.getProductId())) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.getRating())
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);

        // Tăng reviewCount và cập nhật rating
        product.setReviewCount(product.getReviewCount() + 1);
        updateProductRating(product.getProductId()); // Cập nhật cả average rating

        return reviewMapper.toResponse(savedReview);
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findByReviewIdAndUserUserId(reviewId, user.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND_OR_NOT_OWNED));

        // Kiểm tra xem review có thuộc về sản phẩm được chỉ định không
        if (!review.getProduct().getProductId().equals(request.getProductId())) {
            throw new AppException(ErrorCode.INVALID_PRODUCT_FOR_REVIEW);
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updatedReview = reviewRepository.save(review);

        // Cập nhật rating trung bình cho sản phẩm
        updateProductRating(review.getProduct().getProductId());

        return reviewMapper.toResponse(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (!user.getRole().equals(Role.ADMIN) && !review.getUser().getUserId().equals(user.getUserId())) {
            throw new AppException(ErrorCode.NOT_AUTHORIZED);
        }

        Product product = review.getProduct();
        reviewRepository.delete(review);

        // Giảm reviewCount (đảm bảo không âm)
        product.setReviewCount(Math.max(0, product.getReviewCount() - 1));
        updateProductRating(product.getProductId());
    }

    @Override
    public ProductRatingResponse getProductRating(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Double averageRating = reviewRepository.findAverageRatingByProductId(productId).orElse(0.0);
        Integer totalReviews = reviewRepository.countByProductId(productId);

        return ProductRatingResponse.builder()
                .productId(productId)
                .productName(product.getName())
                .averageRating(averageRating)
                .totalReviews(totalReviews)
                .build();
    }

    private void updateProductRating(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Double averageRating = reviewRepository.findAverageRatingByProductId(productId).orElse(0.0);
        product.setRating(averageRating);
        productRepository.save(product); // Lưu cả rating và reviewCount
    }
}