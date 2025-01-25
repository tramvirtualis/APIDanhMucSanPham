package com.example.demo.Repository;

import com.example.demo.Model.otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface otpRepository extends JpaRepository<otp, Long> {
    List<otp> findByEmailAndOtpCodeAndIsUsedFalse(String email, String otpCode);
}
