package com.medical.backend.sales.dto;

import java.time.LocalDate;

public class DailySaleResponse {

    private LocalDate saleDate;
    private Integer cashAmount;
    private Integer onlineAmount;
    private Integer totalAmount;

    // constructor
    public DailySaleResponse(
            LocalDate saleDate,
            Integer cashAmount,
            Integer onlineAmount,
            Integer totalAmount) {
        this.saleDate = saleDate;
        this.cashAmount = cashAmount;
        this.onlineAmount = onlineAmount;
        this.totalAmount = totalAmount;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public Integer getCashAmount() {
        return cashAmount;
    }

    public Integer getOnlineAmount() {
        return onlineAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    // getters
}
