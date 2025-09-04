package com.example.book_store_backend.repository;

import com.example.book_store_backend.entity.DailyOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyOfferRepository extends JpaRepository<DailyOffer, Long> {

    List<DailyOffer> findByIsActiveTrueOrderByCreatedAtDesc();

    @Query("SELECT d FROM DailyOffer d WHERE d.isActive = true AND :currentDate BETWEEN d.startDate AND d.endDate")
    List<DailyOffer> findCurrentActiveOffers(@Param("currentDate") LocalDate currentDate);

    List<DailyOffer> findByBookIdAndIsActiveTrue(Long bookId);

    List<DailyOffer> findByPackIdAndIsActiveTrue(Long packId);

    // Count active offers
    long countByIsActiveTrue();
}