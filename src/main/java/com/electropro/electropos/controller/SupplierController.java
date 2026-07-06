package com.electropro.electropos.controller;

import com.electropro.electropos.dto.SupplierDto;
import com.electropro.electropos.dto.SupplierResponseDto;
import com.electropro.electropos.services.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("/suppliers")
    public SupplierResponseDto saveSupplier(
            @RequestBody SupplierDto dto
    ) {
        return supplierService.saveSupplier(dto);
    }

    @GetMapping("/suppliers")
    public List<SupplierResponseDto> findAllSuppliers() {
        return supplierService.findAllSuppliers();
    }

    @GetMapping("/suppliers/{supplier-id}")
    public SupplierResponseDto findSupplierById(
            @PathVariable("supplier-id") Integer id
    ) {
        return supplierService.findSupplierById(id);
    }

    @PutMapping("/suppliers/{supplier-id}")
    public SupplierResponseDto updateSupplier(
            @PathVariable("supplier-id") Integer id,
            @RequestBody SupplierDto dto
    ) {
        return supplierService.updateSupplier(id, dto);
    }

    @DeleteMapping("/suppliers/{supplier-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSupplier(
            @PathVariable("supplier-id") Integer id
    ) {
        supplierService.deleteSupplier(id);
    }
}