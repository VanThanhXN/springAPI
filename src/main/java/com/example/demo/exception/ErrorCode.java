package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Các mã lỗi hiện có
    USER_EXISTED(1001, "Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1002, "Email đã được đăng ký", HttpStatus.CONFLICT),
    UNAUTHENTICATED(1003, "Chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_ACCOUNT(1005, "Tên đăng nhập hoặc mật khẩu không đúng", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1006, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1007, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(1008, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1009, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),
    PRODUCT_EXISTED(1010, "Sản phẩm đã tồn tại", HttpStatus.CONFLICT),
    INVALID_PRODUCT_DATA(1011, "Thêm thất bại, vui lòng điền đầy đủ thông tin", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1012, "Không tìm thấy sản phẩm", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(1013, "Danh mục đã tồn tại", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(1014, "Không tìm thấy danh mục", HttpStatus.NOT_FOUND),
    CART_ITEM_EXISTED(1015, "Sản phẩm đã có trong giỏ hàng", HttpStatus.CONFLICT),
    CART_ITEM_NOT_FOUND(1016, "Sản phẩm không có trong giỏ hàng", HttpStatus.NOT_FOUND),
    CART_EMPTY(1017, "Giỏ hàng trống", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),

    // Thêm mã lỗi cho INTERNAL_SERVER_ERROR
    INTERNAL_SERVER_ERROR(5000, "Lỗi nội bộ máy chủ", HttpStatus.INTERNAL_SERVER_ERROR);  // Cập nhật mã lỗi và thông báo

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}

