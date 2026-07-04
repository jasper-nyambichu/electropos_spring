package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleResponseDto(
        Integer id,
        String receiptNumber,
        LocalDateTime saleDate,
        BigDecimal totalAmount,
        String paymentMethod,
        String status,
        String customerName
) {
}
