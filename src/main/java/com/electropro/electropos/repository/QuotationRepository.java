package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Customer;
import com.electropro.electropos.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface QuotationRepository extends JpaRepository<Quotation, Integer> {

    List<Quotation> findAllByStatus(String status);

    List<Quotation> findAllByCustomer(Customer customer);
}
