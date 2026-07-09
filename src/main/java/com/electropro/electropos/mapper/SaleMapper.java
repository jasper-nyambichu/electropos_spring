package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.SaleDto;
import com.electropro.electropos.dto.SaleResponseDto;
import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Sale;
import com.electropro.electropos.repository.SaleItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SaleMapper {

    private final SaleItemRepository saleItemRepository;
    private final SaleItemMapper saleItemMapper;

    public SaleMapper(SaleItemRepository saleItemRepository, SaleItemMapper saleItemMapper) {
        this.saleItemRepository = saleItemRepository;
        this.saleItemMapper = saleItemMapper;
    }

    public Sale toSale(SaleDto dto){
        var sale = new Sale();

        var customer = new Customer();
        customer.setId(dto.customerId());
        sale.setCustomer(customer);
        sale.setPaymentMethod(dto.paymentMethod());

        return sale;
    }

    public SaleResponseDto toSaleResponseDto(Sale sale){
        List<com.electropro.electropos.dto.SaleItemResponseDto> items = sale.getId() == null
                ? Collections.emptyList()
                : saleItemRepository.findBySaleId(sale.getId()).stream()
                .map(saleItemMapper::toSaleItemResponseDto)
                .collect(Collectors.toList());

        return new SaleResponseDto(
                sale.getId(),
                sale.getReceiptNumber(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getPaymentMethod(),
                sale.getStatus(),
                sale.getCustomer() != null ? sale.getCustomer().getFirstname() + " " + sale.getCustomer().getLastname() : null,
                items
        );
    }

}