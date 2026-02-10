package com.medical.backend.sales;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_sales", uniqueConstraints = {
        @UniqueConstraint(columnNames = "sale_date")
})
public class DailySale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(nullable = false)
    private Integer cashAmount;

    @Column(nullable = false)
    private Integer onlineAmount;

    @Column(nullable = false)
    private Integer totalAmount;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.totalAmount = cashAmount + onlineAmount;
        this.createdAt = LocalDateTime.now();
    }

    // ✅ GETTERS & SETTERS

    public Long getId() {
        return id;
    }

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

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
