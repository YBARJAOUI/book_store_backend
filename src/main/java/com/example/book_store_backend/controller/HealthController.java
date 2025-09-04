package com.example.book_store_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Health", description = "API de vérification de santé du serveur")
@CrossOrigin(origins = "*")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Vérifier l'état du serveur")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Book Store Backend is running successfully");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Operation(summary = "Informations sur l'API")
    public ResponseEntity<Map<String, Object>> apiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Book Store Backend API");
        response.put("version", "1.0.0");
        response.put("description", "API pour la gestion d'une librairie en ligne");
        response.put("endpoints", Map.of(
                "books", "/api/books",
                "customers", "/api/customers",
                "orders", "/api/orders",
                "packs", "/api/packs",
                "dailyOffers", "/api/daily-offers",
                "statistics", "/api/statistics"
        ));

        return ResponseEntity.ok(response);
    }
}