package com.medical.backend.purchases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByBillNumber(String billNumber);

    List<Purchase> findAllByOrderByBillDateDesc();

    List<Purchase> findByBillDate(LocalDate billDate);

    List<Purchase> findByBillDateBetween(LocalDate start, LocalDate end);

    @Query("""
                SELECT COALESCE(SUM(p.totalAmount), 0)
                FROM Purchase p
                WHERE p.billDate = :date
            """)
    Integer sumPurchasesByDate(LocalDate date);

    @Query("""
                SELECT COALESCE(SUM(p.totalAmount), 0)
                FROM Purchase p
                WHERE p.billDate BETWEEN :start AND :end
            """)
    Integer sumPurchasesByRange(LocalDate start, LocalDate end);

}
