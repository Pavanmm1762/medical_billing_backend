package com.medical.backend.purchases.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PurchasePaymentRequest {

    @NotNull
    @Positive
    private Integer additionalPaidAmount;

    public Integer getAdditionalPaidAmount() {
        return additionalPaidAmount;
    }

    public void setAdditionalPaidAmount(Integer additionalPaidAmount) {
        this.additionalPaidAmount = additionalPaidAmount;
    }

}
