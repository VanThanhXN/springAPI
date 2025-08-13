package com.example.demo.mapper;

import com.example.demo.dto.Response.OrderItemResponse;
import com.example.demo.dto.Response.OrderResponse;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(target = "items", source = "orderItems")
    OrderResponse toOrderResponse(Order order);
}