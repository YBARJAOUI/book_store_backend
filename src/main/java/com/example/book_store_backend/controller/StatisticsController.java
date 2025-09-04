package com.example.book_store_backend.controller;

import com.example.book_store_backend.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "API pour les statistiques du dashboard")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/dashboard")
    @Operation(summary = "Récupérer toutes les statistiques du dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalBooks", statisticsService.getTotalBooksCount());
        stats.put("totalCustomers", statisticsService.getTotalCustomersCount());
        stats.put("totalOrders", statisticsService.getTotalOrdersCount());
        stats.put("totalPacks", statisticsService.getTotalPacksCount());
        stats.put("totalOffers", statisticsService.getTotalOffersCount());
        stats.put("totalRevenue", statisticsService.getTotalRevenue());

        // Statistiques détaillées
        stats.put("activeBooks", statisticsService.getActiveBooksCount());
        stats.put("activeCustomers", statisticsService.getActiveCustomersCount());
        stats.put("pendingOrders", statisticsService.getPendingOrdersCount());
        stats.put("activeOffers", statisticsService.getActiveOffersCount());

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/books/count")
    @Operation(summary = "Nombre total de livres")
    public ResponseEntity<Long> getTotalBooksCount() {
        return ResponseEntity.ok(statisticsService.getTotalBooksCount());
    }

    @GetMapping("/customers/count")
    @Operation(summary = "Nombre total de clients")
    public ResponseEntity<Long> getTotalCustomersCount() {
        return ResponseEntity.ok(statisticsService.getTotalCustomersCount());
    }

    @GetMapping("/orders/count")
    @Operation(summary = "Nombre total de commandes")
    public ResponseEntity<Long> getTotalOrdersCount() {
        return ResponseEntity.ok(statisticsService.getTotalOrdersCount());
    }

    @GetMapping("/packs/count")
    @Operation(summary = "Nombre total de packs")
    public ResponseEntity<Long> getTotalPacksCount() {
        return ResponseEntity.ok(statisticsService.getTotalPacksCount());
    }

    @GetMapping("/offers/count")
    @Operation(summary = "Nombre total d'offres")
    public ResponseEntity<Long> getTotalOffersCount() {
        return ResponseEntity.ok(statisticsService.getTotalOffersCount());
    }

    @GetMapping("/revenue/total")
    @Operation(summary = "Chiffre d'affaires total")
    public ResponseEntity<BigDecimal> getTotalRevenue() {
        return ResponseEntity.ok(statisticsService.getTotalRevenue());
    }

    @GetMapping("/books/active/count")
    @Operation(summary = "Nombre de livres disponibles")
    public ResponseEntity<Long> getActiveBooksCount() {
        return ResponseEntity.ok(statisticsService.getActiveBooksCount());
    }

    @GetMapping("/customers/active/count")
    @Operation(summary = "Nombre de clients actifs")
    public ResponseEntity<Long> getActiveCustomersCount() {
        return ResponseEntity.ok(statisticsService.getActiveCustomersCount());
    }

    @GetMapping("/orders/pending/count")
    @Operation(summary = "Nombre de commandes en attente")
    public ResponseEntity<Long> getPendingOrdersCount() {
        return ResponseEntity.ok(statisticsService.getPendingOrdersCount());
    }

    @GetMapping("/offers/active/count")
    @Operation(summary = "Nombre d'offres actives")
    public ResponseEntity<Long> getActiveOffersCount() {
        return ResponseEntity.ok(statisticsService.getActiveOffersCount());
    }
}