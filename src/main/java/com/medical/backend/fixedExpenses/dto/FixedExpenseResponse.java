package com.medical.backend.fixedExpenses.dto;

import java.time.YearMonth;

public class FixedExpenseResponse {

    private YearMonth month;
    private Integer rent;
    private Integer electricity;
    private Integer others;
    private Integer total;

    public FixedExpenseResponse(
            YearMonth month,
            Integer rent,
            Integer electricity,
            Integer others,
            Integer total) {
        this.month = month;
        this.rent = rent;
        this.electricity = electricity;
        this.others = others;
        this.total = total;
    }

    public YearMonth getMonth() {
        return month;
    }

    public Integer getRent() {
        return rent;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public Integer getOthers() {
        return others;
    }

    public Integer getTotal() {
        return total;
    }
}
