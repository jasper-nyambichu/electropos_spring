package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PurchaseOrderResponseDto(
        Integer id,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status,
        String supplierName,
        List<PurchaseOrderItemResponseDto> items
) {
}