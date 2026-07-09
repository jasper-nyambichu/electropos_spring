package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record PurchaseOrderItemResponseDto(
        Integer id,
        Integer purchaseOrderId,
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitCost,
        BigDecimal subtotal
) {
}