package com.example.taskmanagement.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cookie")
public class CookieController {

    @PostMapping("/set")
    public String setCookie(HttpServletResponse response, @RequestParam String name, @RequestParam String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 gün
        response.addCookie(cookie);
        return "Cookie set.";
    }

    @GetMapping("/get")
    public String getCookie(HttpServletRequest request, @RequestParam String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return "Cookie not found.";
    }

    @PostMapping("/delete")
    public String deleteCookie(HttpServletResponse response, @RequestParam String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "Cookie deleted.";
    }
}
