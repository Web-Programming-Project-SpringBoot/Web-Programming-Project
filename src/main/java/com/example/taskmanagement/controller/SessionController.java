package com.example.taskmanagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    @PostMapping("/set")
    public String setSession(HttpSession session, @RequestParam String key, @RequestParam String value) {
        session.setAttribute(key, value);
        return "Session attribute set.";
    }

    @GetMapping("/get")
    public String getSession(HttpSession session, @RequestParam String key) {
        Object value = session.getAttribute(key);
        return value != null ? value.toString() : "No value found.";
    }

    @PostMapping("/invalidate")
    public String invalidateSession(HttpSession session) {
        session.invalidate();
        return "Session invalidated.";
    }
}
