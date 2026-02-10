package com.medical.backend.dashboard.dto;

public record DashboardResponse(
        DailyDashboard daily,
        MonthlyDashboard monthly) {
    public record DailyDashboard(
            int sales,
            int purchases,
            int otherExpenses,
            int netBalance) {
    }

    public record MonthlyDashboard(
            int totalSales,
            int totalPurchases,
            int otherExpenses,
            int fixedExpenses,
            int netProfit) {
    }
}
