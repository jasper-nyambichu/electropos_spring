package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String name,
        String sku,
        BigDecimal price,
        Integer quantity,
        Integer lowStockThreshold,
        String barcode,
        String imageUrl,
        String categoryName
) {
}
