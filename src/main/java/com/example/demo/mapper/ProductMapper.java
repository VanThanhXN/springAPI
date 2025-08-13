package com.example.demo.mapper;


import com.example.demo.dto.Request.ProductRequest;
import com.example.demo.dto.Response.ProductResponse;
import com.example.demo.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "reviewCount", target = "reviewCount") // Ánh xạ trực tiếp
    ProductResponse toResponse(Product product);
    Product toProduct(ProductRequest productRequest);
    void toUpdatedProduct(ProductRequest productRequest, @MappingTarget Product product);
}