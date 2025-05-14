package com.example.demo.controller;

import com.example.demo.dto.Request.CategoryRequest;
import com.example.demo.dto.Response.CategoryResponse;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Lấy tất cả danh mục
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categories)
                .build();
    }

    // Tạo danh mục mới
    @PostMapping("/add")
    public ApiResponse<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.addCategory(categoryRequest);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .build();
    }

    // Cập nhật danh mục
    @PutMapping("/update/{categoryId}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable Long categoryId,
                                                        @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return ApiResponse.<CategoryResponse>builder()
                .result(updatedCategory)
                .build();
    }

    // Xóa danh mục
    @DeleteMapping("/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .result("Xóa danh mục thành công")
                .build();
    }
}
