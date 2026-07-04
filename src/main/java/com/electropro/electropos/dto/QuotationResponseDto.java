package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.*;


public record QuotationResponseDto(
        Integer id,
        LocalDateTime quotationDate,
        LocalDate expiryDate,
        BigDecimal totalAmount,
        String status,
        String customerName
) {
}
