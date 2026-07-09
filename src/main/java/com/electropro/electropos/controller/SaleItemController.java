package com.electropro.electropos.controller;

import com.electropro.electropos.dto.SaleItemDto;
import com.electropro.electropos.dto.SaleItemResponseDto;
import com.electropro.electropos.services.SaleItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SaleItemController {

    private final SaleItemService saleItemService;

    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @PostMapping("/sale-items")
    public SaleItemResponseDto saveSaleItem(
            @RequestBody SaleItemDto dto
    ) {
        return saleItemService.saveSaleItem(dto);
    }

    @GetMapping("/sale-items")
    public List<SaleItemResponseDto> findAllSaleItems() {
        return saleItemService.findAllSaleItems();
    }

    @GetMapping("/sale-items/{item-id}")
    public SaleItemResponseDto findSaleItemById(
            @PathVariable("item-id") Integer id
    ) {
        return saleItemService.findSaleItemById(id);
    }

    @GetMapping("/sales/{sale-id}/items")
    public List<SaleItemResponseDto> findItemsBySale(
            @PathVariable("sale-id") Integer saleId
    ) {
        return saleItemService.findSaleItemsBySaleId(saleId);
    }

    @DeleteMapping("/sale-items/{item-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSaleItem(
            @PathVariable("item-id") Integer id
    ) {
        saleItemService.deleteSaleItem(id);
    }
}