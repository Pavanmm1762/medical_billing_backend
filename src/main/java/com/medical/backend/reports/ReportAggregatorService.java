package com.medical.backend.reports;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.medical.backend.expenses.OtherExpenseRepository;
import com.medical.backend.fixedExpenses.FixedExpense;
import com.medical.backend.fixedExpenses.FixedExpenseRepository;
import com.medical.backend.purchases.PurchaseRepository;
import com.medical.backend.reports.dto.DailyReportRow;
import com.medical.backend.reports.dto.ExpenseDetailRow;
import com.medical.backend.reports.dto.MonthlyReportResponse;
import com.medical.backend.reports.dto.PurchaseDetailRow;
import com.medical.backend.reports.dto.SaleDetailRow;
import com.medical.backend.reports.dto.SummaryDto;
import com.medical.backend.sales.DailySaleRepository;

@Service
public class ReportAggregatorService {

    private final DailySaleRepository saleRepo;
    private final PurchaseRepository purchaseRepo;
    private final OtherExpenseRepository expenseRepo;
    private final FixedExpenseRepository fixedRepo;

    public ReportAggregatorService(
            DailySaleRepository saleRepo,
            PurchaseRepository purchaseRepo,
            OtherExpenseRepository expenseRepo,
            FixedExpenseRepository fixedRepo) {
        this.saleRepo = saleRepo;
        this.purchaseRepo = purchaseRepo;
        this.expenseRepo = expenseRepo;
        this.fixedRepo = fixedRepo;
    }

    public MonthlyReportResponse buildMonthlyReport(YearMonth month) {

        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();

        // --- SALES ---
        var sales = saleRepo.findBySaleDateBetween(start, end).stream()
                .map(s -> new SaleDetailRow(
                        s.getSaleDate(),
                        s.getCashAmount(),
                        s.getOnlineAmount(),
                        s.getTotalAmount()))
                .toList();

        // --- PURCHASES ---
        var purchases = purchaseRepo.findByBillDateBetween(start, end).stream()
                .map(p -> new PurchaseDetailRow(
                        p.getBillDate(),
                        p.getBillNumber(),
                        p.getSupplier(),
                        p.getTotalAmount(),
                        p.getPaidAmount(),
                        p.getPendingAmount()))
                .toList();

        // --- OTHER EXPENSES ---
        var expenses = expenseRepo.findByExpenseDateBetween(start, end).stream()
                .map(e -> new ExpenseDetailRow(
                        e.getExpenseDate(),
                        e.getCategory(),
                        e.getAmount()))
                .toList();

        // --- FIXED EXPENSES ---
        int fixedExpenses = fixedRepo.findByMonth(month)
                .map(this::calculateFixedExpenseTotal)
                .orElse(0);

        // --- DAILY AGGREGATION ---
        Map<LocalDate, DailyReportRow> dailyMap = new TreeMap<>();

        for (var s : sales) {
            dailyMap.merge(
                    s.date(),
                    new DailyReportRow(s.date(), s.total(), 0, 0, 0),
                    (a, b) -> new DailyReportRow(
                            a.date(),
                            a.sales() + b.sales(),
                            a.purchases(),
                            a.otherExpenses(),
                            0));
        }

        for (var p : purchases) {
            dailyMap.merge(
                    p.billDate(),
                    new DailyReportRow(p.billDate(), 0, p.totalAmount(), 0, 0),
                    (a, b) -> new DailyReportRow(
                            a.date(),
                            a.sales(),
                            a.purchases() + b.purchases(),
                            a.otherExpenses(),
                            0));
        }

        for (var e : expenses) {
            dailyMap.merge(
                    e.date(),
                    new DailyReportRow(e.date(), 0, 0, e.amount(), 0),
                    (a, b) -> new DailyReportRow(
                            a.date(),
                            a.sales(),
                            a.purchases(),
                            a.otherExpenses() + b.otherExpenses(),
                            0));
        }

        var daily = dailyMap.values().stream()
                .map(r -> new DailyReportRow(
                        r.date(),
                        r.sales(),
                        r.purchases(),
                        r.otherExpenses(),
                        r.sales() - r.purchases() - r.otherExpenses()))
                .toList();

        // --- SUMMARY ---
        int totalSales = sales.stream().mapToInt(SaleDetailRow::total).sum();
        int totalPurchases = purchases.stream().mapToInt(PurchaseDetailRow::totalAmount).sum();
        int otherTotal = expenses.stream().mapToInt(ExpenseDetailRow::amount).sum();
        int netProfit = totalSales - totalPurchases - otherTotal - fixedExpenses;

        return new MonthlyReportResponse(
                new SummaryDto(
                        totalSales,
                        totalPurchases,
                        otherTotal,
                        fixedExpenses,
                        netProfit),
                daily,
                sales,
                purchases,
                expenses);
    }

    private int calculateFixedExpenseTotal(FixedExpense f) {
        return (f.getRent() != null ? f.getRent() : 0)
                + (f.getElectricity() != null ? f.getElectricity() : 0)
                + (f.getOthers() != null ? f.getOthers() : 0);
    }
}
