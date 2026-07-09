package com.electropro.electropos.controller;

import com.electropro.electropos.dto.QuotationDto;
import com.electropro.electropos.dto.QuotationResponseDto;
import com.electropro.electropos.services.QuotationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("/quotations")
    public QuotationResponseDto saveQuotation(
            @RequestBody QuotationDto dto
    ) {
        return quotationService.saveQuotation(dto);
    }

    @GetMapping("/quotations")
    public List<QuotationResponseDto> findAllQuotations() {
        return quotationService.findAllQuotations();
    }

    @GetMapping("/quotations/{quotation-id}")
    public QuotationResponseDto findQuotationById(
            @PathVariable("quotation-id") Integer id
    ) {
        return quotationService.findQuotationById(id);
    }

    @PutMapping("/quotations/{quotation-id}")
    public QuotationResponseDto updateQuotation(
            @PathVariable("quotation-id") Integer id,
            @RequestBody QuotationDto dto
    ) {
        return quotationService.updateQuotation(id, dto);
    }

    @PatchMapping("/quotations/{quotation-id}/convert")
    public QuotationResponseDto convertQuotation(
            @PathVariable("quotation-id") Integer id,
            @RequestBody(required = false) com.electropro.electropos.dto.ConvertQuotationDto dto
    ) {
        String paymentMethod = dto != null ? dto.paymentMethod() : null;
        return quotationService.convertQuotation(id, paymentMethod);
    }

    @DeleteMapping("/quotations/{quotation-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuotation(
            @PathVariable("quotation-id") Integer id
    ) {
        quotationService.deleteQuotation(id);
    }
}
