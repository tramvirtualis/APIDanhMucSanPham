package com.example.demo.Service;

import com.example.demo.Model.otp;
import com.example.demo.Repository.otpRepository;
import com.example.demo.Util.emailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class otpService {

    @Autowired
    private otpRepository otpRepository; // Repository để lưu trữ OTP vào cơ sở dữ liệu

    // Thời gian hết hạn của OTP (ví dụ 5 phút)
    private static final int OTP_EXPIRATION_TIME = 5;

    // Hàm tạo và gửi OTP qua email
    public String generateOtp(String email) {
        // Tạo OTP ngẫu nhiên gồm 6 chữ số
        String otpCode = generateRandomOtp();

        // Tạo thời gian hết hạn cho OTP (5 phút kể từ thời điểm tạo)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(OTP_EXPIRATION_TIME);

        // Lưu OTP vào cơ sở dữ liệu
        otp otpEntity = new otp(email, otpCode, expirationTime, false);
        otpRepository.save(otpEntity);  // Lưu OTP vào cơ sở dữ liệu

        // Gửi OTP qua email cho người dùng
        emailUtil.sendOTP(email, otpCode);

        return otpCode; // Trả về mã OTP đã gửi
    }

    // Hàm kiểm tra tính hợp lệ của OTP
    public boolean validateOtp(String email, String otpCode) {
        // Tìm OTP trong cơ sở dữ liệu dựa trên email và mã OTP
        List<otp> otpList = otpRepository.findByEmailAndOtpCodeAndIsUsedFalse(email, otpCode);

        // Nếu không tìm thấy OTP, trả về false
        if (otpList == null || otpList.isEmpty()) {
            return false; // Không tìm thấy OTP hợp lệ
        }

        // Lấy OTP mới nhất (theo thời gian tạo)
        otp otpEntity = otpList.get(otpList.size() - 1);

        // Kiểm tra xem OTP có hết hạn hay không
        if (otpEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            return false; // OTP đã hết hạn
        }

        // Kiểm tra xem OTP đã được sử dụng chưa
        if (otpEntity.getIsUsed()) {
            return false; // OTP đã được sử dụng
        }

        // Đánh dấu OTP là đã sử dụng
        otpEntity.setIsUsed(true);
        otpRepository.save(otpEntity); // Cập nhật trạng thái OTP đã sử dụng trong cơ sở dữ liệu

        return true; // OTP hợp lệ
    }

    // Tạo mã OTP ngẫu nhiên 6 chữ số
    private String generateRandomOtp() {
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // Sinh ngẫu nhiên số từ 100000 đến 999999
        return String.valueOf(otp);
    }
}
