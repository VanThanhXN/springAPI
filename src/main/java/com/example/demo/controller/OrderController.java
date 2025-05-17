package com.example.demo.controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.Request.OrderRequest;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.OrderResponse;
import com.example.demo.dto.Response.OrderStatusResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("Current username: " + username); // Log để debug

        // Thêm @EntityGraph để load các quan hệ cần thiết ngay từ đầu
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getUserOrders() {
        User user = getCurrentUser();
        List<OrderResponse> orders = orderService.getUserOrders(user);
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orders)
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        User user = getCurrentUser();
        OrderResponse order = orderService.createOrder(user, request);
        return ApiResponse.<OrderResponse>builder()
                .result(order)
                .message("Order created successfully")
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderDetails(@PathVariable Long orderId) {
        User user = getCurrentUser();
        OrderResponse order = orderService.getOrderDetails(user, orderId);
        return ApiResponse.<OrderResponse>builder()
                .result(order)
                .build();
    }

    @PutMapping("/{orderId}/cancel")
    public ApiResponse<OrderResponse> cancelOrder(@PathVariable Long orderId) {
        User user = getCurrentUser();
        OrderResponse order = orderService.cancelOrder(user, orderId);
        return ApiResponse.<OrderResponse>builder()
                .result(order)
                .message("Order cancelled successfully")
                .build();
    }

    @GetMapping("/status/{orderId}")
    public ApiResponse<OrderStatusResponse> checkOrderStatus(@PathVariable Long orderId) {
        User user = getCurrentUser();
        OrderStatusResponse status = orderService.checkOrderStatus(user, orderId);
        return ApiResponse.<OrderStatusResponse>builder()
                .result(status)
                .build();
    }
}