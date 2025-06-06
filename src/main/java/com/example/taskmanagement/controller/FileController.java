package com.example.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File dest = new File(uploadDir + File.separator + file.getOriginalFilename());
        file.transferTo(dest);
        return "File uploaded.";
    }

    @GetMapping("/download/{filename}")
    public void downloadFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
        File file = new File(uploadDir + File.separator + filename);
        if (file.exists()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            StreamUtils.copy(new FileInputStream(file), response.getOutputStream());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
