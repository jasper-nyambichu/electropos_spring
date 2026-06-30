package com.electropro.electropos.repository;

import com.electropro.electropos.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Integer> {
}
