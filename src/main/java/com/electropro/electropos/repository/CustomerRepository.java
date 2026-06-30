package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByFirstnameContaining(String firstname);
}
