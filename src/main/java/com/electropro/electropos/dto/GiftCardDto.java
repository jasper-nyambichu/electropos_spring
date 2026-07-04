package com.electropro.electropos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GiftCardDto(
        BigDecimal initialValue,
        LocalDate expiryDate
) {
}
