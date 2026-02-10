package com.medical.backend.expenses.dto;

import java.time.LocalDate;

public class OtherExpenseResponse {

    private LocalDate expenseDate;
    private String category;
    private Integer amount;

    public OtherExpenseResponse(
            LocalDate expenseDate,
            String category,
            Integer amount) {
        this.expenseDate = expenseDate;
        this.category = category;
        this.amount = amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public String getCategory() {
        return category;
    }

    public Integer getAmount() {
        return amount;
    }

}
