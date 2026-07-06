package com.electropro.electropos.services;

import com.electropro.electropos.dto.SupplierDto;
import com.electropro.electropos.dto.SupplierResponseDto;
import com.electropro.electropos.mapper.SupplierMapper;
import com.electropro.electropos.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    public SupplierResponseDto saveSupplier(SupplierDto dto) {
        var supplier = supplierMapper.toSupplier(dto);
        var savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponseDto(savedSupplier);
    }

    public List<SupplierResponseDto> findAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toSupplierResponseDto)
                .collect(Collectors.toList());
    }

    public SupplierResponseDto findSupplierById(Integer id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::toSupplierResponseDto)
                .orElse(null);
    }

    public SupplierResponseDto updateSupplier(Integer id, SupplierDto dto) {
        var existing = supplierRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setName(dto.name());
        existing.setContactPerson(dto.contactPerson());
        existing.setEmail(dto.email());
        existing.setPhone(dto.phone());
        var updated = supplierRepository.save(existing);
        return supplierMapper.toSupplierResponseDto(updated);
    }

    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }
}