package com.example.demo.dto.Request;

import com.example.demo.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUploadequest {
    private String fullName;
    private String phone;
    private String address;
    private Role role;

}
