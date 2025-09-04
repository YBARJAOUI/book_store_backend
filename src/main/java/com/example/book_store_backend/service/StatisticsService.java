package com.example.book_store_backend.service;

import com.example.book_store_backend.entity.OrderStatus;
import com.example.book_store_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StatisticsService {

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final PackRepository packRepository;
    private final DailyOfferRepository dailyOfferRepository;

    /**
     * Nombre total de livres
     */
    public long getTotalBooksCount() {
        return bookRepository.count();
    }

    /**
     * Nombre de livres disponibles
     */
    public long getActiveBooksCount() {
        return bookRepository.findByIsAvailableTrue().size();
    }

    /**
     * Nombre total de clients
     */
    public long getTotalCustomersCount() {
        return customerRepository.count();
    }

    /**
     * Nombre de clients actifs
     */
    public long getActiveCustomersCount() {
        return customerRepository.findByIsActiveTrue().size();
    }

    /**
     * Nombre total de commandes
     */
    public long getTotalOrdersCount() {
        return orderRepository.count();
    }

    /**
     * Nombre de commandes en attente
     */
    public long getPendingOrdersCount() {
        return orderRepository.findByStatus(OrderStatus.PENDING).size();
    }

    /**
     * Nombre total de packs
     */
    public long getTotalPacksCount() {
        return packRepository.count();
    }

    /**
     * Nombre de packs actifs
     */
    public long getActivePacksCount() {
        return packRepository.countByIsActiveTrue();
    }

    /**
     * Nombre total d'offres
     */
    public long getTotalOffersCount() {
        return dailyOfferRepository.count();
    }

    /**
     * Nombre d'offres actives
     */
    public long getActiveOffersCount() {
        return dailyOfferRepository.countByIsActiveTrue();
    }

    /**
     * Chiffre d'affaires total (commandes non annulées)
     */
    public BigDecimal getTotalRevenue() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELLED)
                .map(order -> order.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Revenus du mois en cours
     */
    public BigDecimal getCurrentMonthRevenue() {
        // Cette méthode peut être développée pour calculer les revenus du mois
        return getTotalRevenue(); // Temporairement retourne le total
    }
}