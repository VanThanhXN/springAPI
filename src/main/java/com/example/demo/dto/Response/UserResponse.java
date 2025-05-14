package com.example.demo.dto.Response;
import com.example.demo.entity.Role;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String avatar;
    private Role role;
    private LocalDateTime createdAt;
}