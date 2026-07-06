package com.electropro.electropos.controller;

import com.electropro.electropos.dto.SaleDto;
import com.electropro.electropos.dto.SaleResponseDto;
import com.electropro.electropos.services.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/sales")
    public SaleResponseDto saveSale(
            @RequestBody SaleDto dto
    ) {
        return saleService.saveSale(dto);
    }

    @GetMapping("/sales")
    public List<SaleResponseDto> findAllSales() {
        return saleService.findAllSales();
    }

    @GetMapping("/sales/{sale-id}")
    public SaleResponseDto findSaleById(
            @PathVariable("sale-id") Integer id
    ) {
        return saleService.findSaleById(id);
    }

    @GetMapping("/sales/receipt/{receipt-number}")
    public SaleResponseDto findSaleByReceiptNumber(
            @PathVariable("receipt-number") String receiptNumber
    ) {
        return saleService.findSaleByReceiptNumber(receiptNumber);
    }

    @PatchMapping("/sales/{sale-id}/refund")
    public SaleResponseDto refundSale(
            @PathVariable("sale-id") Integer id
    ) {
        return saleService.refundSale(id);
    }
}