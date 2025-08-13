// ReviewMapper.java
package com.example.demo.mapper;

import com.example.demo.dto.Response.ReviewResponse;
import com.example.demo.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.avatar", target = "userAvatar")
    @Mapping(source = "product.productId", target = "productId")
    ReviewResponse toResponse(Review review);
}