package com.example.taskmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/external")
    public String callExternalApi() {
        String url = "https://api.example.com/data";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}

