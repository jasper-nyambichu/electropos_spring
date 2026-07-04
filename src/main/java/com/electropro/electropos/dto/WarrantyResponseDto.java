package com.electropro.electropos.dto;

import java.time.LocalDate;

public record WarrantyResponseDto(
        Integer id,
        String warrantyNumber,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        String customerName,
        String productName

) {
}
