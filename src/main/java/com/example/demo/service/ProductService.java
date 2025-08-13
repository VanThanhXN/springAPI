package com.example.demo.service;

import com.example.demo.dto.Request.ProductRequest;
import com.example.demo.dto.Response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    // Giữ nguyên các phương thức cũ
    Page<ProductResponse> getAllProducts(Pageable pageable);
    Page<ProductResponse> getAllProducts(Pageable pageable, String username); // Thêm phương thức mới

    ProductResponse getProductById(Long productId);
    ProductResponse getProductById(Long productId, String username); // Thêm phương thức mới

    ProductResponse addProduct(ProductRequest request, MultipartFile image);
    ProductResponse updateProduct(Long productId, ProductRequest productRequest, MultipartFile image);
    void deleteProduct(Long productId);
    List<ProductResponse> searchProducts(String name);
    List<ProductResponse> getProductsByCategory(Long categoryId);
    ProductResponse getTopRatedProduct();
    ProductResponse updateProductRating(Long productId, Double newRating);
}