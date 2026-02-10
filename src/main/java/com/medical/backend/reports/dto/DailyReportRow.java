package com.medical.backend.reports.dto;

import java.time.LocalDate;

public record DailyReportRow(
        LocalDate date,
        int sales,
        int purchases,
        int otherExpenses,
        int profit) {
}
