package com.example.demo.mapper;

import com.example.demo.dto.Response.CartItemResponse;
import com.example.demo.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productImage", source = "product.imageUrl")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "salePrice", source = "product.salePrice")
    @Mapping(target = "subTotal", source = ".", qualifiedByName = "calculateSubTotal")
    @Mapping(target = "addedAt", source = "addedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CartItemResponse toCartItemResponse(Cart cart);

        @Named("calculateSubTotal")
        default BigDecimal calculateSubTotal(Cart cart) {
            BigDecimal price = cart.getProduct().getSalePrice() != null ?
                    cart.getProduct().getSalePrice() :
                    cart.getProduct().getPrice();
            return price.multiply(BigDecimal.valueOf(cart.getQuantity()));
        }
    }