package com.medical.backend.expenses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OtherExpenseRepository
                extends JpaRepository<OtherExpense, Long> {

        List<OtherExpense> findAllByOrderByExpenseDateDesc();

        List<OtherExpense> findByExpenseDate(LocalDate expenseDate);

        List<OtherExpense> findByExpenseDateBetween(
                        LocalDate start,
                        LocalDate end);

        @Query("""
                            SELECT COALESCE(SUM(e.amount), 0)
                            FROM OtherExpense e
                            WHERE e.expenseDate = :date
                        """)
        Integer sumExpensesByDate(LocalDate date);

        @Query("""
                            SELECT COALESCE(SUM(e.amount), 0)
                            FROM OtherExpense e
                            WHERE e.expenseDate BETWEEN :start AND :end
                        """)
        Integer sumExpensesByRange(LocalDate start, LocalDate end);

}
