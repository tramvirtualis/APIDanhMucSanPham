package com.example.demo.Service;

import com.example.demo.Model.account;
import com.example.demo.Repository.accountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class accountService {

    @Autowired
    private accountRepository accountRepository;

    // Regex cho email hợp lệ
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    // Đăng nhập tài khoản
    public boolean login(String email, String password) {
        Optional<account> optionalAccount = accountRepository.findByEmail(email);

        if (optionalAccount.isPresent()) {
            account acc = optionalAccount.get();
            if (acc.getPassword().equals(password)) {
                System.out.println("Login thành công cho email: " + email);
                return true;
            }
        }
        System.err.println("Login thất bại cho email: " + email);
        return false;
    }

    // Đăng ký tài khoản mới
    public boolean register(String email, String password) {
        // Kiểm tra email hợp lệ
        if (!isValidEmail(email)) {
            System.err.println("Email không hợp lệ: " + email);
            return false;
        }

        // Kiểm tra email đã tồn tại chưa
        if (accountRepository.existsByEmail(email)) {
            System.err.println("Email đã được sử dụng: " + email);
            return false;
        }

        // Kiểm tra độ dài mật khẩu
        if (password.length() < 6) {
            System.err.println("Mật khẩu quá ngắn. Phải có ít nhất 6 ký tự.");
            return false;
        }

        // Tạo tài khoản mới
        account newAccount = new account(email, password);
        accountRepository.save(newAccount);
        System.out.println("Đăng ký thành công cho email: " + email);
        return true;
    }

    // Đặt lại mật khẩu
    public boolean resetPassword(String email, String newPassword) {
        // Tìm tài khoản theo email
        Optional<account> optionalAccount = accountRepository.findByEmail(email);

        if (optionalAccount.isPresent()) {
            account acc = optionalAccount.get();
            acc.setPassword(newPassword);
            accountRepository.save(acc);
            System.out.println("Mật khẩu đã được đặt lại thành công cho email: " + email);
            return true;
        }

        System.err.println("Không tìm thấy tài khoản với email: " + email);
        return false;
    }

    // Kiểm tra email hợp lệ
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }
}
