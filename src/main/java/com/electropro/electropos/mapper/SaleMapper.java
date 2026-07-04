package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.SaleDto;
import com.electropro.electropos.dto.SaleResponseDto;
import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Sale;
import org.springframework.stereotype.Service;


@Service
public class SaleMapper {

    public Sale toSale(SaleDto dto){
        var sale = new Sale();

        var customer = new Customer();
        customer.setId(dto.customerId());
        sale.setCustomer(customer);
        sale.setPaymentMethod(dto.paymentMethod());

        return sale;
    }

    public SaleResponseDto toSaleResponseDto(Sale sale){
        return new SaleResponseDto(
                sale.getId(),
                sale.getReceiptNumber(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getPaymentMethod(),
                sale.getStatus(),
                sale.getCustomer() != null ? sale.getCustomer().getFirstname() + " " + sale.getCustomer().getLastname() : null
        );
    }

}
