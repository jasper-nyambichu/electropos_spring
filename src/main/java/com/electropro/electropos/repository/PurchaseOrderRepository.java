package com.electropro.electropos.repository;

import com.electropro.electropos.entity.PurchaseOrder;
import com.electropro.electropos.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findAllByStatus(String status);

    List<PurchaseOrder> findAllBySupplier(Supplier supplier);
}
