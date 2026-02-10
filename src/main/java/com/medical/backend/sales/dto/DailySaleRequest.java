package com.medical.backend.sales.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class DailySaleRequest {

    @NotNull
    private LocalDate saleDate;

    @NotNull
    @PositiveOrZero
    private Integer cashAmount;

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public Integer getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Integer cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Integer getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(Integer onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    @NotNull
    @PositiveOrZero
    private Integer onlineAmount;

    // getters & setters
}
