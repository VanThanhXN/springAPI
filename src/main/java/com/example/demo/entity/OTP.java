package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "otp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long otpId;

    @ManyToOne  // Liên kết nhiều-1 với User
    @JoinColumn(name = "user_id", nullable = false)  // Thêm FK
    private User user;  // Thay thế trường email bằng object User

    @Column(name = "otp_code", nullable = false, length = 6)
    private String otpCode;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

    @Enumerated(EnumType.STRING)
    private OTPPurpose purpose;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}