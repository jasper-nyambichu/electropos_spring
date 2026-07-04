package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.QuotationDto;
import com.electropro.electropos.dto.QuotationResponseDto;
import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Quotation;
import org.springframework.stereotype.Service;

@Service
public class QuotationMapper {

    public Quotation toQuotation(QuotationDto dto) {
        var quotation = new Quotation();

        var customer = new Customer();
        customer.setId(dto.customerId());
        quotation.setCustomer(customer);

        quotation.setExpiryDate(dto.expiryDate());

        return quotation;
    }

    public QuotationResponseDto toQuotationResponseDto(Quotation quotation) {
        return new QuotationResponseDto(
                quotation.getId(),
                quotation.getQuotationDate(),
                quotation.getExpiryDate(),
                quotation.getTotalAmount(),
                quotation.getStatus(),
                quotation.getCustomer() != null ?
                        quotation.getCustomer().getFirstname() + " " + quotation.getCustomer().getLastname() : null
        );
    }
}