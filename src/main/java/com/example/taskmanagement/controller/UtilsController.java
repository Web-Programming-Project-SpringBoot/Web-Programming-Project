package com.example.taskmanagement.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    @GetMapping("/copy")
    public String copyFile() throws IOException {
        File source = new File("source.txt");
        File dest = new File("dest.txt");
        FileUtils.copyFile(source, dest);
        return "File copied.";
    }
}
