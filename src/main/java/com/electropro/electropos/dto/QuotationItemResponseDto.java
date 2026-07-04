package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record QuotationItemResponseDto(
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}
