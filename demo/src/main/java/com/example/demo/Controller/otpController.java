package com.example.demo.Controller;

import com.example.demo.Service.otpService;
import com.example.demo.Service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class otpController {

    @Autowired
    private otpService otpService;

    @Autowired
    private accountService accountService;

    // Gửi OTP
    @PostMapping("/send-otp")
    public boolean sendOtp(@RequestParam String email) {
        String otpCode = otpService.generateOtp(email);
        return true;
    }

    // Đăng ký tài khoản
    @PostMapping("/register")
    public boolean register(@RequestParam String email, @RequestParam String password, @RequestParam String otpCode) {
        // Xác minh OTP
        boolean isOtpValid = otpService.validateOtp(email, otpCode);

        if (!isOtpValid) {
            return false;
        }

        // Đăng ký tài khoản
        boolean isRegistered = accountService.register(email, password);

        if (isRegistered) {
            return true;
        } else {
            return false;
        }
    }
}
