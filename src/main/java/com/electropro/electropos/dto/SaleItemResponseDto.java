package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record SaleItemResponseDto(
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal

) {
}
