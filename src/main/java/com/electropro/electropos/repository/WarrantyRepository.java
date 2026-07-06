package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {
    List<Warranty> findAllByCustomer(Customer customer);


    List<Warranty> findAllByEndDateBefore(LocalDate date);

    Optional<Warranty> findByWarrantyNumber(String warrantyNumber);
}
