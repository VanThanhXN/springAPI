package com.example.demo.service;

import com.example.demo.dto.Request.ChangePasswordRequest;
import com.example.demo.dto.Request.LoginRequest;
import com.example.demo.dto.Request.UserCreateRequest;
import com.example.demo.dto.Response.AuthResponse;
import com.example.demo.dto.Response.UserCreateRepose;
import com.example.demo.dto.Response.UserResponse;

import java.util.UUID;

public interface AuthService {
    UserCreateRepose register(UserCreateRequest request);
    AuthResponse login(LoginRequest request);
    UserResponse getCurrentUser(String username);
    void changePassword(String username, ChangePasswordRequest request);




}