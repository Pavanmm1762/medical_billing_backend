package com.medical.backend.expenses;

import com.medical.backend.expenses.dto.OtherExpenseRequest;
import com.medical.backend.expenses.dto.OtherExpenseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class OtherExpenseController {

    private final OtherExpenseService service;

    public OtherExpenseController(OtherExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OtherExpenseResponse> create(
            @Valid @RequestBody OtherExpenseRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<OtherExpenseResponse>> history() {
        return ResponseEntity.ok(service.history());
    }
}
