package com.electropro.electropos.dto;

public record SupplierResponseDto(
        Integer id,
        String name,
        String contactPerson,
        String email,
        String phone

) {
}
