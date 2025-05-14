package com.example.demo.controller;

import com.example.demo.dto.Request.ChangePasswordRequest;
import com.example.demo.dto.Request.LoginRequest;
import com.example.demo.dto.Request.UserCreateRequest;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.AuthResponse;
import com.example.demo.dto.Response.UserCreateRepose;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserCreateRepose> register(@Valid @RequestBody UserCreateRequest request) {
        UserCreateRepose response = authService.register(request);
        return ApiResponse.<UserCreateRepose>builder()
                .result(response)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ApiResponse.<AuthResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(Authentication authentication) {
        String username = authentication.getName(); // Lấy username từ token
        UserResponse response = authService.getCurrentUser(username);
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .build();
    }
    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser
    ) {
        authService.changePassword(currentUser.getUsername(), request);
        return ApiResponse.<String>builder()
                .result("Password changed successfully.")
                .build();
    }

}