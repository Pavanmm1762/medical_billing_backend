package com.medical.backend.purchases;

import com.medical.backend.exception.ResourceNotFoundException;
import com.medical.backend.purchases.dto.PurchaseEditRequest;
import com.medical.backend.purchases.dto.PurchaseRequest;
import com.medical.backend.purchases.dto.PurchaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    // Create new purchase
    public PurchaseResponse create(PurchaseRequest request) {

        Purchase purchase = new Purchase();
        purchase.setBillNumber(request.getBillNumber());
        purchase.setBillDate(request.getBillDate());
        purchase.setTotalAmount(request.getTotalAmount());
        purchase.setPaidAmount(request.getPaidAmount());
        purchase.setSupplier(request.getSupplier());

        Purchase saved = repository.save(purchase);

        return mapToResponse(saved);
    }

    // Get all purchases (history)
    public List<PurchaseResponse> getHistory() {
        return repository.findAllByOrderByBillDateDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get purchase by bill number
    public PurchaseResponse getByBillNumber(String billNumber) {
        Purchase purchase = repository.findByBillNumber(billNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Purchase bill not found: " + billNumber));

        return mapToResponse(purchase);
    }

    // Add payment to existing purchase
    public PurchaseResponse addPayment(
            String billNumber,
            Integer additionalPaid) {
        Purchase purchase = repository
                .findByBillNumber(billNumber)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        int newPaid = purchase.getPaidAmount() + additionalPaid;

        if (newPaid > purchase.getTotalAmount()) {
            throw new RuntimeException("Paid exceeds bill amount");
        }

        purchase.setPaidAmount(newPaid);
        purchase.setPendingAmount(
                purchase.getTotalAmount() - newPaid);

        Purchase saved = repository.save(purchase);
        return mapToResponse(saved);
    }

    // Edit existing purchase
    public PurchaseResponse edit(
            String billNumber,
            PurchaseEditRequest request) {
        Purchase p = repository.findByBillNumber(billNumber)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (request.getPaidAmount() > request.getTotalAmount()) {
            throw new RuntimeException("Paid exceeds total");
        }

        p.setBillDate(request.getBillDate());
        p.setTotalAmount(request.getTotalAmount());
        p.setPaidAmount(request.getPaidAmount());
        p.setPendingAmount(
                request.getTotalAmount() - request.getPaidAmount());
        p.setSupplier(request.getSupplier());

        return mapToResponse(repository.save(p));
    }

    // Mapping helper
    private PurchaseResponse mapToResponse(Purchase p) {
        return new PurchaseResponse(
                p.getBillNumber(),
                p.getBillDate(),
                p.getTotalAmount(),
                p.getPaidAmount(),
                p.getPendingAmount(),
                p.getSupplier());
    }
}
