package com.medical.backend.dashboard;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.stereotype.Service;

import com.medical.backend.dashboard.dto.DashboardResponse;
import com.medical.backend.expenses.OtherExpenseRepository;
import com.medical.backend.fixedExpenses.FixedExpenseRepository;
import com.medical.backend.purchases.PurchaseRepository;
import com.medical.backend.sales.DailySaleRepository;

@Service
public class DashboardService {

    private final DailySaleRepository saleRepo;
    private final PurchaseRepository purchaseRepo;
    private final OtherExpenseRepository expenseRepo;
    private final FixedExpenseRepository fixedRepo;

    public DashboardService(
            DailySaleRepository saleRepo,
            PurchaseRepository purchaseRepo,
            OtherExpenseRepository expenseRepo,
            FixedExpenseRepository fixedRepo) {
        this.saleRepo = saleRepo;
        this.purchaseRepo = purchaseRepo;
        this.expenseRepo = expenseRepo;
        this.fixedRepo = fixedRepo;
    }

    public DashboardResponse getDashboard() {

        LocalDate today = LocalDate.now();
        YearMonth month = YearMonth.now();

        // --- DAILY ---
        int todaySales = saleRepo.findBySaleDate(today)
                .map(s -> s.getTotalAmount())
                .orElse(0);

        int todayPurchases = purchaseRepo
                .findByBillDate(today)
                .stream()
                .mapToInt(p -> p.getTotalAmount())
                .sum();

        int todayExpenses = expenseRepo
                .findByExpenseDate(today)
                .stream()
                .mapToInt(e -> e.getAmount())
                .sum();

        int todayNet = todaySales - todayPurchases - todayExpenses;

        // --- MONTHLY ---
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        int monthlySales = saleRepo.findBySaleDateBetween(start, end)
                .stream()
                .mapToInt(s -> s.getTotalAmount())
                .sum();

        int monthlyPurchases = purchaseRepo.findByBillDateBetween(start, end)
                .stream()
                .mapToInt(p -> p.getTotalAmount())
                .sum();

        int monthlyOther = expenseRepo.findByExpenseDateBetween(start, end)
                .stream()
                .mapToInt(e -> e.getAmount())
                .sum();

        int fixedExpenses = fixedRepo.findByMonth(month)
                .map(f -> (f.getRent() != null ? f.getRent() : 0) +
                        (f.getElectricity() != null ? f.getElectricity() : 0) +
                        (f.getOthers() != null ? f.getOthers() : 0))
                .orElse(0);

        int netProfit = monthlySales - monthlyPurchases - monthlyOther - fixedExpenses;

        return new DashboardResponse(
                new DashboardResponse.DailyDashboard(
                        todaySales,
                        todayPurchases,
                        todayExpenses,
                        todayNet),
                new DashboardResponse.MonthlyDashboard(
                        monthlySales,
                        monthlyPurchases,
                        monthlyOther,
                        fixedExpenses,
                        netProfit));
    }
}
