package com.electropro.electropos.dto;

public record CustomerDto(
        String firstname,
        String lastname,
        String email,
        String phone,
        String address
) {
}
