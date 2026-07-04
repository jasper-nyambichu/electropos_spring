package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.WarrantyDto;
import com.electropro.electropos.dto.WarrantyResponseDto;
import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.Warranty;
import org.springframework.stereotype.Service;

@Service
public class WarrantyMapper {

    public Warranty toWarranty(WarrantyDto dto) {
        var warranty = new Warranty();

        var customer = new Customer();
        customer.setId(dto.customerId());
        warranty.setCustomer(customer);

        var product = new Product();
        product.setId(dto.productId());
        warranty.setProduct(product);

        warranty.setStartDate(dto.startDate());
        warranty.setEndDate(dto.endDate());

        return warranty;
    }

    public WarrantyResponseDto toWarrantyResponseDto(Warranty warranty) {
        return new WarrantyResponseDto(
                warranty.getId(),
                warranty.getWarrantyNumber(),
                warranty.getStartDate(),
                warranty.getEndDate(),
                warranty.getStatus(),
                warranty.getCustomer() != null ?
                        warranty.getCustomer().getFirstname() + " " + warranty.getCustomer().getLastname() : null,
                warranty.getProduct() != null ? warranty.getProduct().getName() : null
        );
    }
}