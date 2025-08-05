package com.medtech.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from MedTech Application!";
    }
    
    @GetMapping("/status")
    public String status() {
        return "MedTech Application is running successfully!";
    }
} 