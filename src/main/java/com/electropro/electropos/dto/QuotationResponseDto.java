package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;


public record QuotationResponseDto(
        Integer id,
        LocalDateTime quotationDate,
        LocalDate expiryDate,
        BigDecimal totalAmount,
        String status,
        String customerName,
        List<QuotationItemResponseDto> items,
        Integer convertedSaleId
) {
}