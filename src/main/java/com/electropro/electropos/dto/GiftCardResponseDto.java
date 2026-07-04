package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GiftCardResponseDto(
        Integer id,
        String code,
        BigDecimal initialValue,
        BigDecimal currentBalance,
        LocalDate expiryDate,
        String status
) {
}
