package com.electropro.electropos.dto;

public record SaleDto(
        Integer customerId,
        String paymentMethod
) {
}
