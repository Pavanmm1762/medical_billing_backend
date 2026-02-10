package com.medical.backend.reports.dto;

public record SummaryDto(
        int totalSales,
        int totalPurchases,
        int otherExpenses,
        int fixedExpenses,
        int netProfit) {
}
