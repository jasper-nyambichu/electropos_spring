package com.electropro.electropos.services;

import com.electropro.electropos.dto.QuotationDto;
import com.electropro.electropos.dto.QuotationResponseDto;
import com.electropro.electropos.mapper.QuotationMapper;
import com.electropro.electropos.repository.QuotationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final QuotationMapper quotationMapper;

    public QuotationService(QuotationRepository quotationRepository, QuotationMapper quotationMapper) {
        this.quotationRepository = quotationRepository;
        this.quotationMapper = quotationMapper;
    }

    public QuotationResponseDto saveQuotation(QuotationDto dto) {
        var quotation = quotationMapper.toQuotation(dto);
        quotation.setQuotationDate(LocalDateTime.now());
        quotation.setStatus("DRAFT");
        var savedQuotation = quotationRepository.save(quotation);
        return quotationMapper.toQuotationResponseDto(savedQuotation);
    }

    public List<QuotationResponseDto> findAllQuotations() {
        return quotationRepository.findAll()
                .stream()
                .map(quotationMapper::toQuotationResponseDto)
                .collect(Collectors.toList());
    }

    public QuotationResponseDto findQuotationById(Integer id) {
        return quotationRepository.findById(id)
                .map(quotationMapper::toQuotationResponseDto)
                .orElse(null);
    }

    public QuotationResponseDto updateQuotation(Integer id, QuotationDto dto) {
        var existing = quotationRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setExpiryDate(dto.expiryDate());
        var updated = quotationRepository.save(existing);
        return quotationMapper.toQuotationResponseDto(updated);
    }

    public QuotationResponseDto convertQuotation(Integer id) {
        var existing = quotationRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setStatus("CONVERTED");
        var updated = quotationRepository.save(existing);
        return quotationMapper.toQuotationResponseDto(updated);
    }

    public void deleteQuotation(Integer id) {
        quotationRepository.deleteById(id);
    }
}