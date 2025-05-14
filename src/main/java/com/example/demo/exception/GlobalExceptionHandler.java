package com.example.demo.exception;

import com.example.demo.dto.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse response = new ApiResponse();

        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(response);

    }

    @ExceptionHandler(value = BadCredentialsException.class)
    ResponseEntity<ApiResponse> handlingBadCredentialsException(BadCredentialsException exception) {
        ApiResponse response = new ApiResponse();

        response.setCode(ErrorCode.INVALID_ACCOUNT.getCode());
        response.setMessage(ErrorCode.INVALID_ACCOUNT.getMessage());

        return ResponseEntity.status(400).body(response);
    }
}