package com.example.demo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.dto.Request.ProductRequest;
import com.example.demo.dto.Response.ProductResponse;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.ProductService;
import com.example.demo.service.WishlistService;
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
    private final ReviewRepository reviewRepository;
    private final WishlistService wishlistService;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return getAllProducts(pageable, null);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable, String username) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> mapProductToResponse(product, username));
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        return getProductById(productId, null);
    }

    @Override
    public ProductResponse getProductById(Long productId, String username) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return mapProductToResponse(product, username);
    }

    // Các phương thức khác giữ nguyên như cũ
    @Override
    public ProductResponse addProduct(ProductRequest request, MultipartFile image) {
        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_NAME_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);

        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImage(image);
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest, MultipartFile image) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (productRepository.existsByName(productRequest.getName()) &&
                !existingProduct.getName().equalsIgnoreCase(productRequest.getName())) {
            throw new AppException(ErrorCode.PRODUCT_NAME_ALREADY_EXISTS);
        }

        productMapper.toUpdatedProduct(productRequest, existingProduct);

        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadImage(image);
            existingProduct.setImageUrl(imageUrl);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> searchProducts(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(product -> mapProductToResponse(product, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryCategoryId(categoryId);
        return products.stream().map(product -> mapProductToResponse(product, null))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getTopRatedProduct() {
        Product topRated = productRepository.findTopByOrderByRatingDesc()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toResponse(topRated);
    }

    @Override
    public ProductResponse updateProductRating(Long productId, Double newRating) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setRating(newRating);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    private String uploadImage(MultipartFile image) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UPLOAD_FAILED);
        }
    }

    private ProductResponse mapProductToResponse(Product product, String username) {
        ProductResponse response = productMapper.toResponse(product);
        response.setReviewCount(reviewRepository.countReviewsByProductId(product.getProductId()));

        if (username != null) {
            response.setInWishlist(wishlistService.isProductInWishlist(product.getProductId(), username));
        } else {
            response.setInWishlist(false);
        }

        return response;
    }
}