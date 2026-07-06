package com.electropro.electropos.controller;

import com.electropro.electropos.dto.WarrantyDto;
import com.electropro.electropos.dto.WarrantyResponseDto;
import com.electropro.electropos.services.WarrantyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarrantyController {

    private final WarrantyService warrantyService;

    public WarrantyController(WarrantyService warrantyService) {
        this.warrantyService = warrantyService;
    }

    @PostMapping("/warranties")
    public WarrantyResponseDto saveWarranty(
            @RequestBody WarrantyDto dto
    ) {
        return warrantyService.saveWarranty(dto);
    }

    @GetMapping("/warranties")
    public List<WarrantyResponseDto> findAllWarranties() {
        return warrantyService.findAllWarranties();
    }

    @GetMapping("/warranties/{warranty-id}")
    public WarrantyResponseDto findWarrantyById(
            @PathVariable("warranty-id") Integer id
    ) {
        return warrantyService.findWarrantyById(id);
    }

    @GetMapping("/warranties/expiring")
    public List<WarrantyResponseDto> findExpiringWarranties() {
        return warrantyService.findExpiringWarranties();
    }

    @PatchMapping("/warranties/{warranty-id}/claim")
    public WarrantyResponseDto claimWarranty(
            @PathVariable("warranty-id") Integer id
    ) {
        return warrantyService.claimWarranty(id);
    }
}