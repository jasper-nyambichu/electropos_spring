package com.electropro.electropos.dto;

import java.math.BigDecimal;

public record ProductDto(
        String name,
        String sku,
        BigDecimal price,
        BigDecimal costPrice,
        Integer quantity,
        Integer lowStockThreshold,
        String barcode,
        String imageUrl,
        Integer categoryId

) {
}
