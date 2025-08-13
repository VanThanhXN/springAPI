package com.example.demo.Repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryCategoryId(Long categoryId);
    @Query("SELECT p FROM Product p WHERE p.rating IS NOT NULL ORDER BY p.rating DESC LIMIT 1")
    Optional<Product> findTopByOrderByRatingDesc();
    boolean existsByName(String username);
    @Query("SELECT p FROM Product p WHERE p.rating IS NOT NULL AND p.stock > 0 ORDER BY p.rating DESC LIMIT 3")
    List<Product> findTop3ByOrderByRatingDesc();
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category")
    long countByCategory(@Param("category") Category category);
}





