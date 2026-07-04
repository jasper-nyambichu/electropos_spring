package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.SupplierDto;
import com.electropro.electropos.dto.SupplierResponseDto;
import com.electropro.electropos.entity.Supplier;
import org.springframework.stereotype.Service;

@Service
public class SupplierMapper {
    public Supplier toSupplier(SupplierDto dto){
        return new Supplier(
                dto.name(),
                dto.contactPerson(),
                dto.email(),
                dto.phone()
        );
    }

    public SupplierResponseDto toSupplierResponseDto(Supplier supplier){
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactPerson(),
                supplier.getEmail(),
                supplier.getPhone()
        );
    }
}
