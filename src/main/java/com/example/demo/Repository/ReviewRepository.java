// ReviewRepository.java
package com.example.demo.Repository;

import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductProductId(Long productId);

    Optional<Review> findByReviewIdAndUserUserId(Long reviewId, Long userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
    Optional<Double> findAverageRatingByProductId(@Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
    Integer countByProductId(@Param("productId") Long productId);

    boolean existsByUserUserIdAndProductProductId(Long userId, Long productId);
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
    Integer countReviewsByProductId(@Param("productId") Long productId);
    @Query("SELECT AVG(r.rating) FROM Review r")
    Double getAverageRating();
    @Query("SELECT r FROM Review r ORDER BY r.createdAt DESC LIMIT 5")
    List<Review> findTop5ByOrderByCreatedAtDesc();
}
