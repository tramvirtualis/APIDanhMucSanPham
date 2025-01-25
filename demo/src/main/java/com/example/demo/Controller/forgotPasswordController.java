package com.example.demo.Controller;

import com.example.demo.Service.otpService;
import com.example.demo.Service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class forgotPasswordController {

    @Autowired
    private otpService otpService;

    @Autowired
    private accountService accountService;

    // Gửi OTP để quên mật khẩu
    @GetMapping("/forgot-password/send-otp")
    public boolean sendForgotPasswordOtp(@RequestParam String email) {
        otpService.generateOtp(email); // Gửi OTP qua email
        return true; // Trả về true nếu gửi thành công
    }

    // Xác minh OTP và đặt lại mật khẩu
    @PostMapping("/forgot-password/reset")
    public boolean resetPassword(
            @RequestParam String email,
            @RequestParam String otpCode,
            @RequestParam String newPassword
    ) {
        // Xác minh OTP
        boolean isOtpValid = otpService.validateOtp(email, otpCode);

        if (!isOtpValid) {
            return false; // OTP không hợp lệ
        }

        // Đặt lại mật khẩu
        return accountService.resetPassword(email, newPassword);
    }
}
