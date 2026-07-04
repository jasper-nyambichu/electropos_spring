package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.CustomerDto;
import com.electropro.electropos.dto.CustomerResponseDto;
import com.electropro.electropos.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toCustomer(CustomerDto dto){
        return new Customer(
                dto.firstname(),
                dto.lastname(),
                dto.email(),
                dto.phone(),
                dto.address()
        );
    }

    public CustomerResponseDto toCustomerResponseDto(Customer customer){
        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}
