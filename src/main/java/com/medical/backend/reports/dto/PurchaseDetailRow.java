package com.medical.backend.reports.dto;

import java.time.LocalDate;

public record PurchaseDetailRow(
        LocalDate billDate,
        String billNumber,
        String supplier,
        int totalAmount,
        int paidAmount,
        int pendingAmount) {
}
