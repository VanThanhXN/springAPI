package com.example.demo.controller;

import com.example.demo.dto.Request.UserUploadequest;
import com.example.demo.dto.Response.ApiResponse;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .result(users)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ApiResponse.<UserResponse>builder()
                .result(user)
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable("id") Long userId,
                                                @Valid @RequestBody UserUploadequest request) {
        UserResponse response = userService.updateUser(userId, request);
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("Xoá người dùng thành công.")
                .build();
    }

    @PutMapping("/avatar/{id}")
    public ApiResponse<UserResponse> uploadAvatar(@PathVariable("id") Long userId,
                                                  @RequestParam("file") MultipartFile file) {
        UserResponse response = userService.uploadAvatarForUser(userId, file);
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .build();
    }




}