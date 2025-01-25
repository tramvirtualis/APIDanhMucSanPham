package com.example.demo.Controller;

import com.example.demo.Service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class accountController {

    @Autowired
    private accountService accountService;

    @PostMapping("/login")
    public boolean login(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = accountService.login(email, password);

        if (isAuthenticated) {
            return true;
        } else {
            return false;
        }
    }
}
