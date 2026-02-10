package com.medical.backend.reports.dto;

import java.time.LocalDate;

public class DailyReportResponse {

    private LocalDate date;
    private Integer sales;
    private Integer purchases;
    private Integer otherExpenses;
    private Integer netBalance;

    public DailyReportResponse(
            LocalDate date,
            Integer sales,
            Integer purchases,
            Integer otherExpenses,
            Integer netBalance) {
        this.date = date;
        this.sales = sales;
        this.purchases = purchases;
        this.otherExpenses = otherExpenses;
        this.netBalance = netBalance;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getSales() {
        return sales;
    }

    public Integer getPurchases() {
        return purchases;
    }

    public Integer getOtherExpenses() {
        return otherExpenses;
    }

    public Integer getNetBalance() {
        return netBalance;
    }

}
