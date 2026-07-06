package com.electropro.electropos.controller;

import com.electropro.electropos.dto.CustomerDto;
import com.electropro.electropos.dto.CustomerResponseDto;
import com.electropro.electropos.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public CustomerResponseDto saveCustomer(
            @RequestBody CustomerDto dto
    ) {
        return customerService.saveCustomer(dto);
    }

    @GetMapping("/customers")
    public List<CustomerResponseDto> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/customers/{customer-id}")
    public CustomerResponseDto findCustomerById(
            @PathVariable("customer-id") Integer id
    ) {
        return customerService.findCustomerById(id);
    }

    @GetMapping("/customers/search/{customer-name}")
    public List<CustomerResponseDto> findCustomersByName(
            @PathVariable("customer-name") String firstname
    ) {
        return customerService.findCustomersByName(firstname);
    }

    @PutMapping("/customers/{customer-id}")
    public CustomerResponseDto updateCustomer(
            @PathVariable("customer-id") Integer id,
            @RequestBody CustomerDto dto
    ) {
        return customerService.updateCustomer(id, dto);
    }

    @DeleteMapping("/customers/{customer-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(
            @PathVariable("customer-id") Integer id
    ) {
        customerService.deleteCustomer(id);
    }
}