package com.electropro.electropos.dto;

import java.time.LocalDate;

public record WarrantyDto(
        Integer customerId,
        Integer productId,
        LocalDate startDate,
        LocalDate endDate
) {
}
