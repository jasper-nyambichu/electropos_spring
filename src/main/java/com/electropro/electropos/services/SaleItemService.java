package com.electropro.electropos.services;

import com.electropro.electropos.dto.SaleItemDto;
import com.electropro.electropos.dto.SaleItemResponseDto;
import com.electropro.electropos.mapper.SaleItemMapper;
import com.electropro.electropos.repository.SaleItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final SaleItemMapper saleItemMapper;

    public SaleItemService(SaleItemRepository saleItemRepository, SaleItemMapper saleItemMapper) {
        this.saleItemRepository = saleItemRepository;
        this.saleItemMapper = saleItemMapper;
    }

    public SaleItemResponseDto saveSaleItem(SaleItemDto dto) {
        var saleItem = saleItemMapper.toSaleItem(dto);
        var savedSaleItem = saleItemRepository.save(saleItem);
        return saleItemMapper.toSaleItemResponseDto(savedSaleItem);
    }

    public List<SaleItemResponseDto> findAllSaleItems() {
        return saleItemRepository.findAll()
                .stream()
                .map(saleItemMapper::toSaleItemResponseDto)
                .collect(Collectors.toList());
    }

    public SaleItemResponseDto findSaleItemById(Integer id) {
        return saleItemRepository.findById(id)
                .map(saleItemMapper::toSaleItemResponseDto)
                .orElse(null);
    }
}