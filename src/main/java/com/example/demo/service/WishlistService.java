package com.example.demo.service;

import com.example.demo.dto.Request.WishlistRequest;
import com.example.demo.dto.Response.WishlistResponse;

import java.util.List;

public interface WishlistService {
    WishlistResponse addToWishlist(WishlistRequest request, String username);
    void removeFromWishlist(Long productId, String username);
    List<WishlistResponse> getUserWishlist(String username);
    boolean isProductInWishlist(Long productId, String username);
    Integer getWishlistCount(String username);
}