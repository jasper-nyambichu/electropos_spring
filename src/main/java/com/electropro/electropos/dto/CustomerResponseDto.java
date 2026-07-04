package com.electropro.electropos.dto;

public record CustomerResponseDto(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String phone,
        String address
) {
}
