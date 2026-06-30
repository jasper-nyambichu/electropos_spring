package com.electropro.electropos.repository;

import com.electropro.electropos.entity.QuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationItemRepository extends JpaRepository<QuotationItem, Integer> {
}
