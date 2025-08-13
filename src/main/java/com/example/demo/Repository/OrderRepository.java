package com.example.demo.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByOrderIdAndUser(Long orderId, User user);

    List<Order> findAll();
}