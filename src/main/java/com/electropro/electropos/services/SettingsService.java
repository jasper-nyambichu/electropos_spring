package com.electropro.electropos.services;

import com.electropro.electropos.dto.SettingsDto;
import com.electropro.electropos.dto.SettingsResponseDto;
import com.electropro.electropos.entity.BusinessSettings;
import com.electropro.electropos.mapper.SettingsMapper;
import com.electropro.electropos.repository.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    // Business settings is a single config row shared across the app.
    private static final Integer SETTINGS_ID = 1;

    private final SettingsRepository settingsRepository;
    private final SettingsMapper settingsMapper;

    public SettingsService(SettingsRepository settingsRepository, SettingsMapper settingsMapper) {
        this.settingsRepository = settingsRepository;
        this.settingsMapper = settingsMapper;
    }

    public SettingsResponseDto getSettings() {
        var settings = settingsRepository.findById(SETTINGS_ID)
                .orElseGet(this::createDefaultSettings);
        return settingsMapper.toSettingsResponseDto(settings);
    }

    public SettingsResponseDto updateSettings(SettingsDto dto) {
        var existing = settingsRepository.findById(SETTINGS_ID)
                .orElseGet(this::createDefaultSettings);

        existing.setBusinessName(dto.businessName());
        existing.setKraPin(dto.kraPin());
        existing.setAddress(dto.address());
        existing.setCurrency(dto.currency());
        existing.setTimezone(dto.timezone());
        existing.setReceiptHeaderText(dto.receiptHeaderText());
        existing.setReceiptFooterText(dto.receiptFooterText());
        existing.setAutoGenerateSerialNumbers(dto.autoGenerateSerialNumbers());
        existing.setThermalPrinterMode(dto.thermalPrinterMode());

        var saved = settingsRepository.save(existing);
        return settingsMapper.toSettingsResponseDto(saved);
    }

    private BusinessSettings createDefaultSettings() {
        var defaults = new BusinessSettings(
                SETTINGS_ID,
                "My Business",
                "",
                "",
                "KES",
                "nairobi",
                "",
                "",
                true,
                true
        );
        return settingsRepository.save(defaults);
    }
}