package com.medical.backend.reports.dto;

import java.time.LocalDate;

public record ExpenseDetailRow(
        LocalDate date,
        String category,
        int amount) {
}
