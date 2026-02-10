package com.medical.backend.fixedExpenses;

import com.medical.backend.fixedExpenses.dto.FixedExpenseRequest;
import com.medical.backend.fixedExpenses.dto.FixedExpenseResponse;

import java.time.YearMonth;

import org.springframework.stereotype.Service;

@Service
public class FixedExpenseService {

    private final FixedExpenseRepository repository;

    public FixedExpenseService(FixedExpenseRepository repository) {
        this.repository = repository;
    }

    public FixedExpenseResponse saveOrUpdate(FixedExpenseRequest request) {

        FixedExpense expense = repository
                .findByMonth(request.getMonth())
                .orElse(new FixedExpense());

        expense.setMonth(request.getMonth());
        expense.setRent(request.getRent());
        expense.setElectricity(request.getElectricity());
        expense.setOthers(request.getOthers());

        FixedExpense saved = repository.save(expense);

        int total = (saved.getRent() == null ? 0 : saved.getRent()) +
                (saved.getElectricity() == null ? 0 : saved.getElectricity()) +
                (saved.getOthers() == null ? 0 : saved.getOthers());

        return new FixedExpenseResponse(
                saved.getMonth(),
                saved.getRent(),
                saved.getElectricity(),
                saved.getOthers(),
                total);
    }

    public FixedExpenseResponse getByMonth(YearMonth month) {
        return repository.findByMonth(month)
                .map(e -> {
                    int total = (e.getRent() == null ? 0 : e.getRent()) +
                            (e.getElectricity() == null ? 0 : e.getElectricity()) +
                            (e.getOthers() == null ? 0 : e.getOthers());

                    return new FixedExpenseResponse(
                            e.getMonth(),
                            e.getRent(),
                            e.getElectricity(),
                            e.getOthers(),
                            total);
                })
                .orElse(null);
    }
}
