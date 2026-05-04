package com.rumbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RumboApplication {

    public static void main(String[] args) {
        SpringApplication.run(RumboApplication.class, args);
    }

    @GetMapping("/api/health")
    public String health() {
        return "RUMBO Backend is running!";
    }
}
