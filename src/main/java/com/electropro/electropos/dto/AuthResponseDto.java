package com.electropro.electropos.dto;

public record AuthResponseDto(
        String token,
        String role,
        String email,
        String firstname
) {}