package com.example.demo.Repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
//    List<Cart> findByUser_UserId(Long userId);


}
