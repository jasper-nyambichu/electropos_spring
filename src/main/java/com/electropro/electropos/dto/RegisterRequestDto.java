package com.electropro.electropos.dto;

public record RegisterRequestDto(
        String firstname,
        String lastname,
        String email,
        String password,
        String role
) {}