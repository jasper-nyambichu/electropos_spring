package com.electropro.electropos.controller;

import com.electropro.electropos.dto.QuotationItemDto;
import com.electropro.electropos.dto.QuotationItemResponseDto;
import com.electropro.electropos.services.QuotationItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuotationItemController {

    private final QuotationItemService quotationItemService;

    public QuotationItemController(QuotationItemService quotationItemService) {
        this.quotationItemService = quotationItemService;
    }

    @PostMapping("/quotation-items")
    public QuotationItemResponseDto saveQuotationItem(
            @RequestBody QuotationItemDto dto
    ) {
        return quotationItemService.saveQuotationItem(dto);
    }

    @GetMapping("/quotation-items")
    public List<QuotationItemResponseDto> findAllQuotationItems() {
        return quotationItemService.findAllQuotationItems();
    }

    @GetMapping("/quotation-items/{item-id}")
    public QuotationItemResponseDto findQuotationItemById(
            @PathVariable("item-id") Integer id
    ) {
        return quotationItemService.findQuotationItemById(id);
    }

    @GetMapping("/quotations/{quotation-id}/items")
    public List<QuotationItemResponseDto> findItemsByQuotation(
            @PathVariable("quotation-id") Integer quotationId
    ) {
        return quotationItemService.findItemsByQuotationId(quotationId);
    }

    @DeleteMapping("/quotation-items/{item-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuotationItem(
            @PathVariable("item-id") Integer id
    ) {
        quotationItemService.deleteQuotationItem(id);
    }
}