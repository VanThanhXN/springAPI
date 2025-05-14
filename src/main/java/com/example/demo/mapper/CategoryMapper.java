package com.example.demo.mapper;

import com.example.demo.dto.Request.CategoryRequest;
import com.example.demo.dto.Response.CategoryResponse;
import com.example.demo.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);
    Category toCategory(CategoryRequest categoryRequest);
    void toUpdatedCategory(CategoryRequest categoryRequest, @MappingTarget Category category);
}
