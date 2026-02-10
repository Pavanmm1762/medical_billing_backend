package com.medical.backend.reports.dto;

import java.util.List;

public record MonthlyReportResponse(
        SummaryDto summary,
        List<DailyReportRow> daily,
        List<SaleDetailRow> sales,
        List<PurchaseDetailRow> purchases,
        List<ExpenseDetailRow> otherExpenses) {
}
