package com.example.demo.dto.Response;

import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRepose {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Role role; // Enum vai trò (ADMIN, CUSTOMER, SELLER)


}