package com.medical.backend.sales;

import com.medical.backend.sales.dto.DailySaleRequest;
import com.medical.backend.sales.dto.DailySaleResponse;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DailySaleService {

    private final DailySaleRepository repository;

    public DailySaleService(DailySaleRepository repository) {
        this.repository = repository;
    }

    // Save or update daily sale
    public DailySaleResponse saveOrUpdate(DailySaleRequest request) {

        DailySale sale = repository
                .findBySaleDate(request.getSaleDate())
                .orElse(new DailySale());

        sale.setSaleDate(request.getSaleDate());
        sale.setCashAmount(request.getCashAmount());
        sale.setOnlineAmount(request.getOnlineAmount());
        sale.setTotalAmount(request.getCashAmount() + request.getOnlineAmount());

        DailySale saved = repository.save(sale);

        return new DailySaleResponse(saved.getSaleDate(), saved.getCashAmount(), saved.getOnlineAmount(),
                saved.getTotalAmount());
    }

    // Get daily sale by date
    public DailySaleResponse getByDate(LocalDate date) {
        return repository.findBySaleDate(date)
                .map(sale -> new DailySaleResponse(
                        sale.getSaleDate(),
                        sale.getCashAmount(),
                        sale.getOnlineAmount(),
                        sale.getTotalAmount()))
                .orElse(null);
    }

    public List<DailySaleResponse> history() {
        return repository.findAllByOrderBySaleDateDesc()
                .stream()
                .map(this::map)
                .toList();
    }

    private DailySaleResponse map(DailySale sale) {
        return new DailySaleResponse(
                sale.getSaleDate(),
                sale.getCashAmount(),
                sale.getOnlineAmount(),
                sale.getTotalAmount());
    }
}
