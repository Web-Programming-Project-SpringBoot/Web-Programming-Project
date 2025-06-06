package com.example.taskmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiConsumeController {

    @GetMapping("/external-api")
    @ResponseBody
    public String getExternalData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.github.com";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
