package com.electropro.electropos.dto;

public record AuthRequestDto(
        String email,
        String password
) {}