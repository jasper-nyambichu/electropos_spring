package com.electropro.electropos.repository;

import com.electropro.electropos.entity.QuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationItemRepository extends JpaRepository<QuotationItem, Integer> {
    List<QuotationItem> findByQuotationId(Integer quotationId);
}