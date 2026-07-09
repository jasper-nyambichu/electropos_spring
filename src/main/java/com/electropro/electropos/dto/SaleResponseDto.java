package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDto(
        Integer id,
        String receiptNumber,
        LocalDateTime saleDate,
        BigDecimal totalAmount,
        String paymentMethod,
        String status,
        String customerName,
        List<SaleItemResponseDto> items
) {
}