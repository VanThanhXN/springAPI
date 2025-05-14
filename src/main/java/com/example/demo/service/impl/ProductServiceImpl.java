package com.example.demo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;

import com.example.demo.dto.Request.ProductRequest;
import com.example.demo.dto.Response.ProductResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductStatus;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::toResponse);
    }

    @Override
    public ProductResponse addProduct(ProductRequest request, MultipartFile image) {
        // Tạo một đối tượng Product từ ProductRequest
        Product product = productMapper.toProduct(request);

        // Upload ảnh nếu có và cập nhật URL ảnh
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImage(image);
            product.setImageUrls(imageUrl);
        }

        // Lưu sản phẩm mới
        Product savedProduct = productRepository.save(product);

        // Trả về ProductResponse từ sản phẩm đã lưu
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest, MultipartFile image) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Ánh xạ thông tin cập nhật từ ProductRequest vào Product đã tồn tại
        productMapper.toUpdatedProduct(productRequest, existingProduct);

        // Upload ảnh và cập nhật URL ảnh nếu có
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImage(image);  // Gọi phương thức uploadImage
            existingProduct.setImageUrls(imageUrl);  // Cập nhật URL ảnh vào sản phẩm
        }

        // Lưu sản phẩm đã cập nhật
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(productMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
        return products.stream().map(productMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getTopRatedProduct() {
        Product topRated = productRepository.findTopByOrderByRatingDesc()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toResponse(topRated);
    }

    private String uploadImage(MultipartFile image) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);  // Cải thiện xử lý lỗi
        }
    }
}
