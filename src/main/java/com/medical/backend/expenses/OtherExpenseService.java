package com.medical.backend.expenses;

import com.medical.backend.expenses.dto.OtherExpenseRequest;
import com.medical.backend.expenses.dto.OtherExpenseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherExpenseService {

    private final OtherExpenseRepository repository;

    public OtherExpenseService(OtherExpenseRepository repository) {
        this.repository = repository;
    }

    public OtherExpenseResponse create(OtherExpenseRequest request) {

        OtherExpense expense = new OtherExpense();
        expense.setExpenseDate(request.getExpenseDate());
        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());

        OtherExpense saved = repository.save(expense);

        return map(saved);
    }

    public List<OtherExpenseResponse> history() {
        return repository.findAllByOrderByExpenseDateDesc()
                .stream()
                .map(this::map)
                .toList();
    }

    private OtherExpenseResponse map(OtherExpense e) {
        return new OtherExpenseResponse(
                e.getExpenseDate(),
                e.getCategory(),
                e.getAmount());
    }
}
