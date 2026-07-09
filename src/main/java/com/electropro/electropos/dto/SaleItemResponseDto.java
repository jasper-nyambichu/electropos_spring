package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record SaleItemResponseDto(
        Integer id,
        Integer saleId,
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {
}
