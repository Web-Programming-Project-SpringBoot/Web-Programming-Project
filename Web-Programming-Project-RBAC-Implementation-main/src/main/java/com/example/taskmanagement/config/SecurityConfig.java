package com.example.taskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırak
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated() // /api/** endpoint’leri yetkilendirme gerektirir
                .anyRequest().permitAll() // Diğer tüm istekler için yetkilendirme gerektirme
            )
            .httpBasic(); // Basic Auth kullan

        return http.build();
    }
}