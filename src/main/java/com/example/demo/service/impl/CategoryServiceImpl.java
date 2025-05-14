package com.example.demo.service.impl;


import com.example.demo.Repository.CategoryRepository;
import com.example.demo.dto.Request.CategoryRequest;
import com.example.demo.dto.Response.CategoryResponse;
import com.example.demo.entity.Category;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        // Kiểm tra danh mục đã tồn tại chưa
        if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.toCategory(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        categoryMapper.toUpdatedCategory(categoryRequest, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponse> searchCategories(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }


}
