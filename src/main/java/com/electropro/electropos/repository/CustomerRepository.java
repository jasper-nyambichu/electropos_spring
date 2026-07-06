package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByFirstnameContaining(String firstname);
}
