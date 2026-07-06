package com.electropro.electropos.services;

import com.electropro.electropos.dto.CustomerDto;
import com.electropro.electropos.dto.CustomerResponseDto;
import com.electropro.electropos.mapper.CustomerMapper;
import com.electropro.electropos.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponseDto saveCustomer(CustomerDto dto) {
        var customer = customerMapper.toCustomer(dto);
        var savedCustomer = customerRepository.save(customer);
        return customerMapper.toCustomerResponseDto(savedCustomer);
    }

    public List<CustomerResponseDto> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerResponseDto)
                .collect(Collectors.toList());
    }

    public CustomerResponseDto findCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerResponseDto)
                .orElse(null);
    }

    public List<CustomerResponseDto> findCustomersByName(String firstname) {
        return customerRepository.findAllByFirstnameContaining(firstname)
                .stream()
                .map(customerMapper::toCustomerResponseDto)
                .collect(Collectors.toList());
    }

    public CustomerResponseDto updateCustomer(Integer id, CustomerDto dto) {
        var existing = customerRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setFirstname(dto.firstname());
        existing.setLastname(dto.lastname());
        existing.setEmail(dto.email());
        existing.setPhone(dto.phone());
        existing.setAddress(dto.address());
        var updated = customerRepository.save(existing);
        return customerMapper.toCustomerResponseDto(updated);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}