package com.example.taskmanagement.controller;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Controller
public class FileUploadController {

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";

    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        File uploadFolder = new File(UPLOAD_DIR);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();

        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, file.getBytes());

        
        Thumbnails.of(path.toFile())
                .size(200, 200)
                .toFile(new File(UPLOAD_DIR + "thumb_" + file.getOriginalFilename()));

        return "Dosya yüklendi ve küçük görsel oluşturuldu.";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload"; 
    }
}
