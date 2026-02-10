package com.medical.backend.fixedExpenses.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.YearMonth;

public class FixedExpenseRequest {

    @NotNull(message = "Month is required")
    private YearMonth month;

    @PositiveOrZero(message = "Rent must be zero or positive")
    private Integer rent;

    @PositiveOrZero(message = "Electricity must be zero or positive")
    private Integer electricity;

    @PositiveOrZero(message = "Others must be zero or positive")
    private Integer others;

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public void setElectricity(Integer electricity) {
        this.electricity = electricity;
    }

    public Integer getOthers() {
        return others;
    }

    public void setOthers(Integer others) {
        this.others = others;
    }

}
