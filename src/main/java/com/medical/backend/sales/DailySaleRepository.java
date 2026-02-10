package com.medical.backend.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailySaleRepository extends JpaRepository<DailySale, Long> {

    Optional<DailySale> findBySaleDate(LocalDate saleDate);

    List<DailySale> findAllByOrderBySaleDateDesc();

    @Query("""
                SELECT COALESCE(SUM(d.totalAmount), 0)
                FROM DailySale d
                WHERE d.saleDate = :date
            """)
    Integer sumSalesByDate(LocalDate date);

    List<DailySale> findBySaleDateBetween(
            LocalDate start,
            LocalDate end);
}
