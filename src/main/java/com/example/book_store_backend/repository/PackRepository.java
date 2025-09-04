package com.example.book_store_backend.repository;

import com.example.book_store_backend.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    List<Pack> findByIsActiveTrueOrderByCreatedAtDesc();

    List<Pack> findByIsFeaturedTrue();

    List<Pack> findByCategoryAndIsActiveTrue(String category);

    List<Pack> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    @Query("SELECT DISTINCT p.category FROM Pack p WHERE p.category IS NOT NULL AND p.isActive = true")
    List<String> findDistinctCategories();

    // Count active packs
    long countByIsActiveTrue();
}