package com.example.demo.Model;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "otps") // Tên bảng trong cơ sở dữ liệu
public class otp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "otp_code", nullable = false, length = 10)
    private String otpCode;

    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;

    @Column(name = "is_used", nullable = false) // Trạng thái OTP đã dùng hay chưa
    private Boolean isUsed = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Constructor không tham số
    public otp() {}

    // Constructor đầy đủ
    public otp(String email, String otpCode, LocalDateTime expirationTime, Boolean isUsed) {
        this.email = email;
        this.otpCode = otpCode;
        this.expirationTime = expirationTime;
        this.isUsed = isUsed;
        this.createdAt = LocalDateTime.now();
    }

    // Getter và Setter
    public Long getOtpId() {
        return otpId;
    }

    public void setOtpId(Long otpId) {
        this.otpId = otpId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}