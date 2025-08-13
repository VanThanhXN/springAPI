package com.example.demo.controller;

import com.example.demo.dto.Request.WishlistRequest;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.WishlistResponse;
import com.example.demo.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add")
    public ApiResponse<WishlistResponse> addToWishlist(@RequestBody WishlistRequest request) {
        String username = getCurrentUsername();
        WishlistResponse response = wishlistService.addToWishlist(request, username);
        return ApiResponse.<WishlistResponse>builder()
                .result(response)
                .message("Sản phẩm đã được thêm vào wishlist")
                .build();
    }

    @DeleteMapping("/remove/{productId}")
    public ApiResponse<String> removeFromWishlist(@PathVariable Long productId) {
        String username = getCurrentUsername();
        wishlistService.removeFromWishlist(productId, username);
        return ApiResponse.<String>builder()
                .result("Sản phẩm đã được xóa khỏi wishlist")
                .build();
    }

    @GetMapping("/my-wishlist")
    public ApiResponse<List<WishlistResponse>> getUserWishlist() {
        String username = getCurrentUsername();
        List<WishlistResponse> wishlist = wishlistService.getUserWishlist(username);
        return ApiResponse.<List<WishlistResponse>>builder()
                .result(wishlist)
                .build();
    }

    @GetMapping("/check/{productId}")
    public ApiResponse<Boolean> isProductInWishlist(@PathVariable Long productId) {
        String username = getCurrentUsername();
        boolean isInWishlist = wishlistService.isProductInWishlist(productId, username);
        return ApiResponse.<Boolean>builder()
                .result(isInWishlist)
                .build();
    }

    @GetMapping("/count")
    public ApiResponse<Integer> getWishlistCount() {
        String username = getCurrentUsername();
        Integer count = wishlistService.getWishlistCount(username);
        return ApiResponse.<Integer>builder()
                .result(count)
                .build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}