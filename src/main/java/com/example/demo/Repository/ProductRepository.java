package com.example.demo.Repository;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryCategoryId(Long categoryId);
    @Query("SELECT p FROM Product p WHERE p.rating IS NOT NULL ORDER BY p.rating DESC LIMIT 1")
    Optional<Product> findTopByOrderByRatingDesc();


}
