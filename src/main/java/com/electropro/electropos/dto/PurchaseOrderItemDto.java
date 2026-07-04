package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record PurchaseOrderItemDto(
        Integer purchaseOrderId,
        Integer productId,
        Integer quantity,
        BigDecimal unitCost
) {
}
