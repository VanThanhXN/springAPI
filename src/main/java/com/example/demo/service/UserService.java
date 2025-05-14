package com.example.demo.service;

import com.example.demo.dto.Request.ChangePasswordRequest;
import com.example.demo.dto.Request.UserUploadequest;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long userId, UserUploadequest request);
    UserResponse uploadAvatarForUser(Long userId, MultipartFile file);
    void deleteUser(Long userId);

}