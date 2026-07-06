package com.electropro.electropos.services;

import com.electropro.electropos.dto.WarrantyDto;
import com.electropro.electropos.dto.WarrantyResponseDto;
import com.electropro.electropos.mapper.WarrantyMapper;
import com.electropro.electropos.repository.WarrantyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;
    private final WarrantyMapper warrantyMapper;

    public WarrantyService(WarrantyRepository warrantyRepository, WarrantyMapper warrantyMapper) {
        this.warrantyRepository = warrantyRepository;
        this.warrantyMapper = warrantyMapper;
    }

    public WarrantyResponseDto saveWarranty(WarrantyDto dto) {
        var warranty = warrantyMapper.toWarranty(dto);
        warranty.setWarrantyNumber("WRN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        warranty.setStatus("ACTIVE");
        var savedWarranty = warrantyRepository.save(warranty);
        return warrantyMapper.toWarrantyResponseDto(savedWarranty);
    }

    public List<WarrantyResponseDto> findAllWarranties() {
        return warrantyRepository.findAll()
                .stream()
                .map(warrantyMapper::toWarrantyResponseDto)
                .collect(Collectors.toList());
    }

    public WarrantyResponseDto findWarrantyById(Integer id) {
        return warrantyRepository.findById(id)
                .map(warrantyMapper::toWarrantyResponseDto)
                .orElse(null);
    }

    public List<WarrantyResponseDto> findExpiringWarranties() {
        return warrantyRepository.findAllByEndDateBefore(LocalDate.now().plusDays(30))
                .stream()
                .map(warrantyMapper::toWarrantyResponseDto)
                .collect(Collectors.toList());
    }

    public WarrantyResponseDto claimWarranty(Integer id) {
        var existing = warrantyRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setStatus("CLAIMED");
        var updated = warrantyRepository.save(existing);
        return warrantyMapper.toWarrantyResponseDto(updated);
    }
}