package com.example.demo.service.impl;

import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.WishlistRepository;
import com.example.demo.dto.Request.WishlistRequest;
import com.example.demo.dto.Response.WishlistResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.entity.Wishlist;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.WishlistMapper;
import com.example.demo.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;

    @Override
    public WishlistResponse addToWishlist(WishlistRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Kiểm tra xem sản phẩm đã có trong wishlist chưa
        if (wishlistRepository.existsByUserUserIdAndProductProductId(user.getUserId(), product.getProductId())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_IN_WISHLIST);
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .addedAt(LocalDateTime.now())
                .build();

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.toResponse(savedWishlist);
    }

    @Override
    public void removeFromWishlist(Long productId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Wishlist wishlist = wishlistRepository.findByUserUserIdAndProductProductId(user.getUserId(), productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_IN_WISHLIST));

        wishlistRepository.delete(wishlist);
    }

    @Override
    public List<WishlistResponse> getUserWishlist(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Wishlist> wishlists = wishlistRepository.findByUserUserId(user.getUserId());
        return wishlists.stream()
                .map(wishlistMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isProductInWishlist(Long productId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return wishlistRepository.existsByUserUserIdAndProductProductId(user.getUserId(), productId);
    }

    @Override
    public Integer getWishlistCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return wishlistRepository.countByUserId(user.getUserId());
    }
}