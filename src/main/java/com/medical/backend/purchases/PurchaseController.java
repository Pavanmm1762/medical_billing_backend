package com.medical.backend.purchases;

import com.medical.backend.purchases.dto.PurchaseEditRequest;
import com.medical.backend.purchases.dto.PurchasePaymentRequest;
import com.medical.backend.purchases.dto.PurchaseRequest;
import com.medical.backend.purchases.dto.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@CrossOrigin(origins = "*")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    // Create purchase bill
    @PostMapping
    public ResponseEntity<PurchaseResponse> create(
            @Valid @RequestBody PurchaseRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    // List history
    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> history() {
        return ResponseEntity.ok(service.getHistory());
    }

    // Get by bill number
    @GetMapping("/{billNumber}")
    public ResponseEntity<PurchaseResponse> getByBill(
            @PathVariable String billNumber) {
        return ResponseEntity.ok(service.getByBillNumber(billNumber));
    }

    //
    @PatchMapping("/{billNumber}/pay")
    public ResponseEntity<PurchaseResponse> addPayment(
            @PathVariable String billNumber,
            @Valid @RequestBody PurchasePaymentRequest request) {
        return ResponseEntity.ok(
                service.addPayment(
                        billNumber,
                        request.getAdditionalPaidAmount()));
    }

    // Edit existing bill
    @PutMapping("/{billNumber}")
    public ResponseEntity<PurchaseResponse> edit(
            @PathVariable String billNumber,
            @Valid @RequestBody PurchaseEditRequest request) {
        return ResponseEntity.ok(
                service.edit(billNumber, request));
    }
}
