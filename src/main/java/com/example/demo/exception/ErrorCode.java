package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Mã lỗi người dùng
    USER_EXISTED(1001, "Tên đăng nhập đã tồn tại", HttpStatus.CONFLICT),
    EMAIL_EXISTED(1002, "Email đã được đăng ký", HttpStatus.CONFLICT),
    UNAUTHENTICATED(1003, "Chưa xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1004, "Không có quyền truy cập", HttpStatus.FORBIDDEN),
    INVALID_ACCOUNT(1005, "Tên đăng nhập hoặc mật khẩu không đúng", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1006, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1007, "Token đã hết hạn", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(1008, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1009, "Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST),

    // Mã lỗi sản phẩm
    PRODUCT_EXISTED(1010, "Sản phẩm đã tồn tại", HttpStatus.CONFLICT),
    INVALID_PRODUCT_DATA(1011, "Thêm thất bại, vui lòng điền đầy đủ thông tin", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1012, "Không tìm thấy sản phẩm", HttpStatus.NOT_FOUND),
    PRODUCT_INVALID_CATEGORY(3001, "Danh mục sản phẩm không hợp lệ", HttpStatus.BAD_REQUEST),
    PRODUCT_OUT_OF_STOCK(3002, "Sản phẩm đã hết hàng", HttpStatus.BAD_REQUEST),
    PRODUCT_INVALID_PRICE(3003, "Giá sản phẩm không hợp lệ", HttpStatus.BAD_REQUEST),
    PRODUCT_INVALID_DISCOUNT(3004, "Giảm giá không hợp lệ", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_ALREADY_EXISTS(3005, "Tên sản phẩm đã tồn tại", HttpStatus.CONFLICT),

    // Mã lỗi danh mục
    CATEGORY_EXISTED(1013, "Danh mục đã tồn tại", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(1014, "Không tìm thấy danh mục", HttpStatus.NOT_FOUND),

    // Mã lỗi giỏ hàng
    CART_ITEM_EXISTED(1015, "Sản phẩm đã có trong giỏ hàng", HttpStatus.CONFLICT),
    CART_ITEM_NOT_FOUND(1016, "Sản phẩm không có trong giỏ hàng", HttpStatus.NOT_FOUND),
    CART_EMPTY(1017, "Giỏ hàng trống", HttpStatus.BAD_REQUEST),
    INVALID_QUANTITY(1018, "Số lượng phải lớn hơn 0", HttpStatus.BAD_REQUEST),

    // Mã lỗi đơn hàng
    ORDER_NOT_FOUND(4001, "Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),
    ORDER_CANNOT_BE_CANCELLED(4002, "Không thể hủy đơn hàng ở trạng thái hiện tại", HttpStatus.BAD_REQUEST),
    CART_IS_EMPTY(4003, "Không thể tạo đơn hàng khi giỏ hàng trống", HttpStatus.BAD_REQUEST),

    // Mã lỗi đánh giá sản phẩm
    REVIEW_NOT_FOUND(4004, "Đánh giá không tồn tại", HttpStatus.NOT_FOUND),
    REVIEW_ALREADY_EXISTS(4005, "Bạn đã đánh giá sản phẩm này rồi", HttpStatus.CONFLICT),
    REVIEW_NOT_FOUND_OR_NOT_OWNED(4006, "Đánh giá không tồn tại hoặc không thuộc về bạn", HttpStatus.FORBIDDEN),
    INVALID_PRODUCT_FOR_REVIEW(4007, "Sản phẩm không khớp với đánh giá", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED(4008, "Bạn không có quyền thực hiện hành động này", HttpStatus.FORBIDDEN),

    // Mã lỗi xử lý ảnh
    UPLOAD_FAILED(2001, "Lỗi khi tải lên ảnh", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_IMAGE_FAILED(2002, "Lỗi khi xóa ảnh", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FILE_TYPE(2003, "File không phải là ảnh hợp lệ", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE(2004, "Kích thước file vượt quá giới hạn 5MB", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_URL(2005, "URL ảnh không hợp lệ", HttpStatus.BAD_REQUEST),
    IMAGE_PROCESSING_ERROR(2006, "Lỗi khi xử lý ảnh", HttpStatus.INTERNAL_SERVER_ERROR),

    // Mã lỗi hệ thống
    INVALID_DATA(4000, "Dữ liệu không hợp lệ", HttpStatus.BAD_REQUEST),
    SERVICE_UNAVAILABLE(5001, "Dịch vụ tạm thời gián đoạn", HttpStatus.SERVICE_UNAVAILABLE),
    EXTERNAL_SERVICE_ERROR(5002, "Lỗi dịch vụ bên ngoài", HttpStatus.FAILED_DEPENDENCY),
    DATABASE_ERROR(5003, "Lỗi truy vấn cơ sở dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR(5000, "Lỗi nội bộ máy chủ", HttpStatus.INTERNAL_SERVER_ERROR),
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_ALREADY_IN_WISHLIST(6001, "Sản phẩm đã có trong wishlist", HttpStatus.CONFLICT),
    WISHLIST_ITEM_NOT_FOUND(6002, "Mục wishlist không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_IN_WISHLIST(6003, "Sản phẩm không có trong wishlist", HttpStatus.NOT_FOUND),
    AI_SERVICE_ERROR(5004, "Lỗi khi gọi dịch vụ AI", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
