package com.medical.backend.sales;

import com.medical.backend.sales.dto.DailySaleRequest;
import com.medical.backend.sales.dto.DailySaleResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "*")
public class DailySaleController {

    private final DailySaleService service;

    public DailySaleController(DailySaleService service) {
        this.service = service;
    }

    // Save or update daily sale
    @PostMapping
    public ResponseEntity<DailySaleResponse> saveSales(
            @Valid @RequestBody DailySaleRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(request));
    }

    // Get daily sale by date
    @GetMapping
    public ResponseEntity<DailySaleResponse> getByDate(
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(service.getByDate(date));
    }

    // Get sales history
    @GetMapping("/history")
    public ResponseEntity<List<DailySaleResponse>> history() {
        return ResponseEntity.ok(service.history());
    }

}
