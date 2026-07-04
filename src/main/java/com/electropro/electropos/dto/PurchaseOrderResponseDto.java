package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseOrderResponseDto(
        Integer id,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status,
        String supplierName
) {
}
