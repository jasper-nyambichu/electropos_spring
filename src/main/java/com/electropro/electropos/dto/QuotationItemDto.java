package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record QuotationItemDto(
        Integer quotationId,
        Integer productId,
        Integer quantity,
        BigDecimal unitPrice

) {
}
