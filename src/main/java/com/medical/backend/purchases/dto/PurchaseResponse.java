package com.medical.backend.purchases.dto;

import java.time.LocalDate;

public class PurchaseResponse {

    private String billNumber;
    private LocalDate billDate;
    private Integer totalAmount;
    private Integer paidAmount;
    private Integer pendingAmount;
    private String supplier;

    public PurchaseResponse(
            String billNumber,
            LocalDate billDate,
            Integer totalAmount,
            Integer paidAmount,
            Integer pendingAmount,
            String supplier) {
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.pendingAmount = pendingAmount;
        this.supplier = supplier;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public Integer getPaidAmount() {
        return paidAmount;
    }

    public Integer getPendingAmount() {
        return pendingAmount;
    }

    public String getSupplier() {
        return supplier;
    }

}
