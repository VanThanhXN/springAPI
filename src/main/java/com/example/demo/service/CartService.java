package com.example.demo.service;

import com.example.demo.dto.Request.CartRequest;
import com.example.demo.dto.Response.CartResponse;
import com.example.demo.entity.User;

public interface CartService {
    CartResponse getCart(User user);
    CartResponse addToCart(User user, CartRequest request);
    CartResponse updateCartItem(User user, Long itemId, Integer quantity);
    CartResponse removeCartItem(User user, Long itemId);
    void clearCart(User user);
}