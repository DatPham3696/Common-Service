package com.example.gateway_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("")
    public ResponseEntity<String> fallback() {
        return ResponseEntity.ok("IAM Service is currently unavailable. Please try again later.");
    }
    @GetMapping("/iam")
    public ResponseEntity<String> iamFallback() {
        return ResponseEntity.ok("IAM Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/storage")
    public ResponseEntity<String> storageFallback() {
        return ResponseEntity.ok("Storage Service is currently unavailable. Please try again later.");
    }
}
