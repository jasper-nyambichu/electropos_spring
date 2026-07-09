package com.electropro.electropos.controller;

import com.electropro.electropos.dto.PurchaseOrderItemDto;
import com.electropro.electropos.dto.PurchaseOrderItemResponseDto;
import com.electropro.electropos.services.PurchaseOrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseOrderItemController {

    private final PurchaseOrderItemService purchaseOrderItemService;

    public PurchaseOrderItemController(PurchaseOrderItemService purchaseOrderItemService) {
        this.purchaseOrderItemService = purchaseOrderItemService;
    }

    @PostMapping("/purchase-order-items")
    public PurchaseOrderItemResponseDto savePurchaseOrderItem(
            @RequestBody PurchaseOrderItemDto dto
    ) {
        return purchaseOrderItemService.savePurchaseOrderItem(dto);
    }

    @GetMapping("/purchase-order-items")
    public List<PurchaseOrderItemResponseDto> findAllPurchaseOrderItems() {
        return purchaseOrderItemService.findAllPurchaseOrderItems();
    }

    @GetMapping("/purchase-order-items/{item-id}")
    public PurchaseOrderItemResponseDto findPurchaseOrderItemById(
            @PathVariable("item-id") Integer id
    ) {
        return purchaseOrderItemService.findPurchaseOrderItemById(id);
    }

    @GetMapping("/purchases/{purchase-id}/items")
    public List<PurchaseOrderItemResponseDto> findItemsByPurchaseOrder(
            @PathVariable("purchase-id") Integer purchaseOrderId
    ) {
        return purchaseOrderItemService.findItemsByPurchaseOrderId(purchaseOrderId);
    }

    @DeleteMapping("/purchase-order-items/{item-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePurchaseOrderItem(
            @PathVariable("item-id") Integer id
    ) {
        purchaseOrderItemService.deletePurchaseOrderItem(id);
    }
}