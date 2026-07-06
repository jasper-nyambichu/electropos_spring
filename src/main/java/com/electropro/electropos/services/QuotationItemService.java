package com.electropro.electropos.services;

import com.electropro.electropos.dto.QuotationItemDto;
import com.electropro.electropos.dto.QuotationItemResponseDto;
import com.electropro.electropos.mapper.QuotationItemMapper;
import com.electropro.electropos.repository.QuotationItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationItemService {

    private final QuotationItemRepository quotationItemRepository;
    private final QuotationItemMapper quotationItemMapper;

    public QuotationItemService(QuotationItemRepository quotationItemRepository, QuotationItemMapper quotationItemMapper) {
        this.quotationItemRepository = quotationItemRepository;
        this.quotationItemMapper = quotationItemMapper;
    }

    public QuotationItemResponseDto saveQuotationItem(QuotationItemDto dto) {
        var quotationItem = quotationItemMapper.toQuotationItem(dto);
        var savedItem = quotationItemRepository.save(quotationItem);
        return quotationItemMapper.toQuotationItemResponseDto(savedItem);
    }

    public List<QuotationItemResponseDto> findAllQuotationItems() {
        return quotationItemRepository.findAll()
                .stream()
                .map(quotationItemMapper::toQuotationItemResponseDto)
                .collect(Collectors.toList());
    }

    public QuotationItemResponseDto findQuotationItemById(Integer id) {
        return quotationItemRepository.findById(id)
                .map(quotationItemMapper::toQuotationItemResponseDto)
                .orElse(null);
    }
}