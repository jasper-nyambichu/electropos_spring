package com.electropro.electropos.repository;

import com.electropro.electropos.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    Optional<Sale> findByReceiptNumber(String receiptNumber);

    List<Sale> findAllBySaleDateBetween(LocalDateTime start, LocalDateTime end);
}
