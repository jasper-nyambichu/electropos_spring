package com.electropro.electropos.controller;

import com.electropro.electropos.dto.SettingsDto;
import com.electropro.electropos.dto.SettingsResponseDto;
import com.electropro.electropos.services.SettingsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/settings")
    public SettingsResponseDto getSettings() {
        return settingsService.getSettings();
    }

    @PutMapping("/settings")
    public SettingsResponseDto updateSettings(@RequestBody SettingsDto dto) {
        return settingsService.updateSettings(dto);
    }
}
