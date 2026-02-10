package com.medical.backend.reports.dto;

import java.time.LocalDate;

public record SaleDetailRow(
        LocalDate date,
        int cash,
        int online,
        int total) {
}
