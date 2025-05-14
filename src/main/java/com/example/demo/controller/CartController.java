package com.example.demo.controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.Request.CartRequest;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.CartResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @GetMapping
    public ApiResponse<CartResponse> getCart() {
        User user = getCurrentUser();
        CartResponse cart = cartService.getCart(user);
        return ApiResponse.<CartResponse>builder().result(cart).build();
    }

    @PostMapping("/add")
    public ApiResponse<CartResponse> addToCart(@RequestBody CartRequest request) {
        User user = getCurrentUser();
        CartResponse cart = cartService.addToCart(user, request);
        return ApiResponse.<CartResponse>builder()
                .result(cart)
                .message("Product added to cart successfully")
                .build();
    }

    @PutMapping("/update/{itemId}")
    public ApiResponse<CartResponse> updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        User user = getCurrentUser();
        CartResponse cart = cartService.updateCartItem(user, itemId, quantity);
        return ApiResponse.<CartResponse>builder()
                .result(cart)
                .message("Cart item updated successfully")
                .build();
    }

    @DeleteMapping("/remove/{itemId}")
    public ApiResponse<CartResponse> removeCartItem(@PathVariable Long itemId) {
        User user = getCurrentUser();
        CartResponse cart = cartService.removeCartItem(user, itemId);
        return ApiResponse.<CartResponse>builder()
                .result(cart)
                .message("Product removed from cart successfully")
                .build();
    }

    @DeleteMapping("/clear")
    public ApiResponse<String> clearCart() {
        User user = getCurrentUser();
        cartService.clearCart(user);
        return ApiResponse.<String>builder()
                .result("Cart cleared successfully")
                .build();
    }


}