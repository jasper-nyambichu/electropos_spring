package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record QuotationItemResponseDto(
        Integer id,
        Integer quotationId,
        Integer productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}