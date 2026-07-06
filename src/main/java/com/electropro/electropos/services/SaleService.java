package com.electropro.electropos.services;

import com.electropro.electropos.dto.SaleDto;
import com.electropro.electropos.dto.SaleResponseDto;
import com.electropro.electropos.mapper.SaleMapper;
import com.electropro.electropos.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    public SaleService(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    public SaleResponseDto saveSale(SaleDto dto) {
        var sale = saleMapper.toSale(dto);
        sale.setSaleDate(LocalDateTime.now());
        sale.setReceiptNumber("RCP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        sale.setStatus("COMPLETED");
        var savedSale = saleRepository.save(sale);
        return saleMapper.toSaleResponseDto(savedSale);
    }

    public List<SaleResponseDto> findAllSales() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toSaleResponseDto)
                .collect(Collectors.toList());
    }

    public SaleResponseDto findSaleById(Integer id) {
        return saleRepository.findById(id)
                .map(saleMapper::toSaleResponseDto)
                .orElse(null);
    }

    public SaleResponseDto findSaleByReceiptNumber(String receiptNumber) {
        return saleRepository.findByReceiptNumber(receiptNumber)
                .map(saleMapper::toSaleResponseDto)
                .orElse(null);
    }

    public SaleResponseDto refundSale(Integer id) {
        var existing = saleRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setStatus("REFUNDED");
        var updated = saleRepository.save(existing);
        return saleMapper.toSaleResponseDto(updated);
    }
}