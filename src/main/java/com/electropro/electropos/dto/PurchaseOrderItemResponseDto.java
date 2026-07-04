package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record PurchaseOrderItemResponseDto(
        String productName,
        Integer quantity,
        BigDecimal unitCost,
        BigDecimal subtotal
) {
}
