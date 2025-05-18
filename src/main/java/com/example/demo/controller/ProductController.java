package com.example.demo.controller;

import com.example.demo.dto.Request.ProductRequest;
import com.example.demo.dto.Response.ProductResponse;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductResponse> products = productService.getAllProducts(PageRequest.of(page, size));
        return ApiResponse.<Page<ProductResponse>>builder().result(products).build();
    }

    @PostMapping("/add")
    public ApiResponse<ProductResponse> addProduct(@ModelAttribute ProductRequest productRequest,
                                                   @RequestParam(value = "image", required = false) MultipartFile image) {
        ProductResponse response = productService.addProduct(productRequest, image);
        return ApiResponse.<ProductResponse>builder()
                .result(response)
                .build();
    }

    @PutMapping("/upload/{productId}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId,
                                                      @RequestPart("product") ProductRequest productRequest,
                                                      @RequestPart(value = "image", required = false) MultipartFile image) {
        ProductResponse updatedProduct = productService.updateProduct(productId, productRequest, image);
        return ApiResponse.<ProductResponse>builder()
                .result(updatedProduct)
                .message("Sản phẩm đã được cập nhật thành công!")
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder()
                .result("Xóa sản phẩm thành công")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> searchProducts(@RequestParam String name) {
        List<ProductResponse> products = productService.searchProducts(name);
        return ApiResponse.<List<ProductResponse>>builder().result(products).build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
        return ApiResponse.<List<ProductResponse>>builder().result(products).build();
    }
    // Trong file ProductController.java
    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse product = productService.getProductById(productId);
        return ApiResponse.<ProductResponse>builder()
                .result(product)
                .build();
    }
}