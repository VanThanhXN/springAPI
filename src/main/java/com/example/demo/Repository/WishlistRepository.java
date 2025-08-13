package com.example.demo.Repository;

import com.example.demo.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUserUserId(Long userId);

    Optional<Wishlist> findByUserUserIdAndProductProductId(Long userId, Long productId);

    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.user.userId = :userId")
    Integer countByUserId(Long userId);

    boolean existsByUserUserIdAndProductProductId(Long userId, Long productId);
}