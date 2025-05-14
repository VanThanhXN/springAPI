package com.example.demo.service.impl;

import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.dto.Request.CartRequest;
import com.example.demo.dto.Response.CartItemResponse;
import com.example.demo.dto.Response.CartResponse;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.CartMapper;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(User user) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        return buildCartResponse(cartItems);
    }

    @Override
    @Transactional
    public CartResponse addToCart(User user, CartRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Check if product already in cart
        Cart existingCartItem = cartRepository.findByUserAndProduct(user, product)
                .orElse(null);

        if (existingCartItem != null) {
            // Update quantity if already exists
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            cartRepository.save(existingCartItem);
        } else {
            // Add new item to cart
            Cart newCartItem = Cart.builder()
                    .user(user)
                    .product(product)
                    .quantity(request.getQuantity())
                    .addedAt(LocalDateTime.now())
                    .build();
            cartRepository.save(newCartItem);
        }

        return getCart(user);
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(User user, Long itemId, Integer quantity) {
        if (quantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        Cart cartItem = cartRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (!cartItem.getUser().equals(user)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cartItem);

        return getCart(user);
    }

    @Override
    @Transactional
    public CartResponse removeCartItem(User user, Long itemId) {
        Cart cartItem = cartRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        if (!cartItem.getUser().equals(user)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        cartRepository.delete(cartItem);
        return getCart(user);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

    private CartResponse buildCartResponse(List<Cart> cartItems) {
        List<CartItemResponse> items = cartItems.stream()
                .map(cartMapper::toCartItemResponse)
                .collect(Collectors.toList());

        BigDecimal totalPrice = items.stream()
                .map(CartItemResponse::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .items(items)
                .totalPrice(totalPrice)
                .totalItems(items.size())
                .build();
    }
}