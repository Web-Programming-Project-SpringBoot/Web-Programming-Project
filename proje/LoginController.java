package com.example.taskmanagement.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html dosyasını döndürür
    }

    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session) {
        return "dashboard"; // dashboard.html dosyasını döndürür
    }
}
