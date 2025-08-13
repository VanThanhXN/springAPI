package com.example.demo.service;

import com.example.demo.dto.Request.OrderRequest;
import com.example.demo.dto.Response.OrderResponse;
import com.example.demo.dto.Response.OrderStatusResponse;
import com.example.demo.entity.User;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getUserOrders(User user);
    OrderResponse createOrder(User user, OrderRequest request);
    OrderResponse getOrderDetails(User user, Long orderId);
    OrderResponse cancelOrder(User user, Long orderId);
    OrderStatusResponse checkOrderStatus(User user, Long orderId);
    List<OrderResponse> getAllOrders();
}