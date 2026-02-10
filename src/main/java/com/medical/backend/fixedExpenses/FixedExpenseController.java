package com.medical.backend.fixedExpenses;

import com.medical.backend.fixedExpenses.dto.FixedExpenseRequest;
import com.medical.backend.fixedExpenses.dto.FixedExpenseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/fixed-expenses")
@CrossOrigin(origins = "*")
public class FixedExpenseController {

    private final FixedExpenseService service;

    public FixedExpenseController(FixedExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FixedExpenseResponse> save(
            @Valid @RequestBody FixedExpenseRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(request));
    }

    @GetMapping
    public ResponseEntity<FixedExpenseResponse> get(
            @RequestParam YearMonth month) {

        FixedExpenseResponse response = service.getByMonth(month);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);
    }
}
