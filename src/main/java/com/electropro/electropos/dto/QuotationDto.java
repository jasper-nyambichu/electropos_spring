package com.electropro.electropos.dto;

import java.time.LocalDate;

public record QuotationDto(
        Integer customerId,
        LocalDate expiryDate

) {
}
