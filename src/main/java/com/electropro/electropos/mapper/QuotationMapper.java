package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.QuotationDto;
import com.electropro.electropos.dto.QuotationResponseDto;
import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Quotation;
import com.electropro.electropos.repository.QuotationItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationMapper {

    private final QuotationItemRepository quotationItemRepository;
    private final QuotationItemMapper quotationItemMapper;

    public QuotationMapper(QuotationItemRepository quotationItemRepository,
                           QuotationItemMapper quotationItemMapper) {
        this.quotationItemRepository = quotationItemRepository;
        this.quotationItemMapper = quotationItemMapper;
    }

    public Quotation toQuotation(QuotationDto dto) {
        var quotation = new Quotation();

        var customer = new Customer();
        customer.setId(dto.customerId());
        quotation.setCustomer(customer);

        quotation.setExpiryDate(dto.expiryDate());

        return quotation;
    }

    public QuotationResponseDto toQuotationResponseDto(Quotation quotation) {
        List<com.electropro.electropos.dto.QuotationItemResponseDto> items = quotation.getId() == null
                ? Collections.emptyList()
                : quotationItemRepository.findByQuotationId(quotation.getId()).stream()
                .map(quotationItemMapper::toQuotationItemResponseDto)
                .collect(Collectors.toList());

        return new QuotationResponseDto(
                quotation.getId(),
                quotation.getQuotationDate(),
                quotation.getExpiryDate(),
                quotation.getTotalAmount(),
                quotation.getStatus(),
                quotation.getCustomer() != null ?
                        quotation.getCustomer().getFirstname() + " " + quotation.getCustomer().getLastname() : null,
                items,
                quotation.getConvertedSaleId()
        );
    }
}