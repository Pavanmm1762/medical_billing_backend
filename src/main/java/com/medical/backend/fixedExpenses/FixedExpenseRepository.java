package com.medical.backend.fixedExpenses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Optional;

public interface FixedExpenseRepository
        extends JpaRepository<FixedExpense, Long> {

    Optional<FixedExpense> findByMonth(YearMonth month);
}
