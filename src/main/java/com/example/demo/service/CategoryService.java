package com.example.demo.service;

import com.example.demo.dto.Request.CategoryRequest;
import com.example.demo.dto.Response.CategoryResponse;
import com.example.demo.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse addCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
    List<CategoryResponse> searchCategories(String name);

}
