package com.electropro.electropos.dto;

public record SaleItemDto(
        Integer saleId,
        Integer productId,
        Integer quantity
) {
}
