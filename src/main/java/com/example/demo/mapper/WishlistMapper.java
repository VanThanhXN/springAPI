package com.example.demo.mapper;

import com.example.demo.dto.Response.WishlistResponse;
import com.example.demo.entity.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductMapper.class)
public interface WishlistMapper {
    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

    @Mapping(source = "product", target = "productDetails")
    WishlistResponse toResponse(Wishlist wishlist);
}