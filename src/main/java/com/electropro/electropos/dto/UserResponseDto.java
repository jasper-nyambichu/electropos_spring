package com.electropro.electropos.dto;

public record UserResponseDto(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String role
) {}