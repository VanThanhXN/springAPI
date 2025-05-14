package com.example.demo.Repository;

import com.example.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, Long> {
// Lấy giỏ hàng của user

    List<Cart> findByUser_UserId(Long userId);
}
