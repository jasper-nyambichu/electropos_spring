package com.electropro.electropos.controller;

import com.electropro.electropos.dto.PurchaseOrderDto;
import com.electropro.electropos.dto.PurchaseOrderResponseDto;
import com.electropro.electropos.services.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("/purchases")
    public PurchaseOrderResponseDto savePurchaseOrder(
            @RequestBody PurchaseOrderDto dto
    ) {
        return purchaseOrderService.savePurchaseOrder(dto);
    }

    @GetMapping("/purchases")
    public List<PurchaseOrderResponseDto> findAllPurchaseOrders() {
        return purchaseOrderService.findAllPurchaseOrders();
    }

    @GetMapping("/purchases/{purchase-id}")
    public PurchaseOrderResponseDto findPurchaseOrderById(
            @PathVariable("purchase-id") Integer id
    ) {
        return purchaseOrderService.findPurchaseOrderById(id);
    }

    @PatchMapping("/purchases/{purchase-id}/receive")
    public PurchaseOrderResponseDto receivePurchaseOrder(
            @PathVariable("purchase-id") Integer id
    ) {
        return purchaseOrderService.receivePurchaseOrder(id);
    }

    @DeleteMapping("/purchases/{purchase-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePurchaseOrder(
            @PathVariable("purchase-id") Integer id
    ) {
        purchaseOrderService.deletePurchaseOrder(id);
    }
}