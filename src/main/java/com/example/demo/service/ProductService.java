package com.example.demo.service;

import com.example.demo.dto.Request.ProductRequest;
import com.example.demo.dto.Response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable);
    ProductResponse addProduct(ProductRequest productRequest, MultipartFile image); // Cập nhật với MultipartFile
    ProductResponse updateProduct(Long productId, ProductRequest productRequest, MultipartFile image); // Cập nhật với MultipartFile
    void deleteProduct(Long productId);
    List<ProductResponse> searchProducts(String name);
    List<ProductResponse> getProductsByCategory(Long categoryId);
    ProductResponse getTopRatedProduct();
}
