package com.example.taskmanagement.controller;

import com.example.taskmanagement.service.JwtService;
import com.example.taskmanagement.service.CustomUserDetailsService;
import com.example.taskmanagement.service.UserService;
import com.example.taskmanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            
            User user = userService.findByUsername(loginRequest.getUsername());
            if (user == null) {
                throw new RuntimeException("Kullanıcı bulunamadı");
            }
            response.put("success", true);
            response.put("token", token);
            response.put("tokenType", "Bearer");
            response.put("user", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail()
            ));
            response.put("message", "Giriş başarılı");
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            response.put("success", false);
            response.put("error", "Kullanıcı adı veya şifre hatalı");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Giriş sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest registerRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kullanıcı zaten var mı kontrol et
            if (userService.findByUsername(registerRequest.getUsername()) != null) {
                response.put("success", false);
                response.put("error", "Bu kullanıcı adı zaten kullanımda");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Yeni kullanıcı oluştur
            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setEnabled(true);
            
            User savedUser = userService.createUser(user);
            
            response.put("success", true);
            response.put("message", "Kullanıcı başarıyla kayıt edildi");
            response.put("user", Map.of(
                "id", savedUser.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Kayıt sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody TokenValidationRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isValid = jwtService.validateToken(request.getToken());
            
            if (isValid) {
                String username = jwtService.extractUsername(request.getToken());
                User user = userService.findByUsername(username);
                if (user == null) {
                    throw new RuntimeException("Kullanıcı bulunamadı");
                }
                
                response.put("success", true);
                response.put("valid", true);
                response.put("user", Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail()
                ));
            } else {
                response.put("success", true);
                response.put("valid", false);
                response.put("message", "Token geçersiz veya süresi dolmuş");
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("valid", false);
            response.put("error", "Token doğrulama sırasında hata: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // DTOs
    public static class LoginRequest {
        private String username;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class TokenValidationRequest {
        private String token;
        
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}

