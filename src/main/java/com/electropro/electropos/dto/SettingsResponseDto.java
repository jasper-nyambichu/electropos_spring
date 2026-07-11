package com.electropro.electropos.dto;

public record SettingsResponseDto(
        Integer id,
        String businessName,
        String kraPin,
        String address,
        String currency,
        String timezone,
        String receiptHeaderText,
        String receiptFooterText,
        boolean autoGenerateSerialNumbers,
        boolean thermalPrinterMode
) {}