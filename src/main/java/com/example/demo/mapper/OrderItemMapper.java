package com.example.demo.mapper;

import com.example.demo.dto.Response.OrderItemResponse;
import com.example.demo.dto.Response.OrderResponse;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productImage", source = "product.imageUrl")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
