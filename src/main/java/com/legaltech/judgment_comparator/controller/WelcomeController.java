package com.legaltech.judgment_comparator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Welcome/Health Check Controller
 */
@RestController
public class WelcomeController {

    /**
     * GET / - Root endpoint
     */
    @GetMapping("/")
    public Map<String, Object> welcome() {
        return Map.of(
                "application", "AI Powered Court Judgment Comparator",
                "version", "1.0.0",
                "status", "running",
                "timestamp", LocalDateTime.now(),
                "endpoints", Map.of(
                        "judgments", "/api/judgments",
                        "users", "/api/users",
                        "h2Console", "/h2-console"));
    }

    /**
     * GET /health - Health check
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
