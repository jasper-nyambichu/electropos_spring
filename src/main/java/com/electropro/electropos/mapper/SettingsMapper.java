package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.SettingsResponseDto;
import com.electropro.electropos.entity.BusinessSettings;
import org.springframework.stereotype.Service;

@Service
public class SettingsMapper {

    public SettingsResponseDto toSettingsResponseDto(BusinessSettings settings) {
        return new SettingsResponseDto(
                settings.getId(),
                settings.getBusinessName(),
                settings.getKraPin(),
                settings.getAddress(),
                settings.getCurrency(),
                settings.getTimezone(),
                settings.getReceiptHeaderText(),
                settings.getReceiptFooterText(),
                settings.isAutoGenerateSerialNumbers(),
                settings.isThermalPrinterMode()
        );
    }
}