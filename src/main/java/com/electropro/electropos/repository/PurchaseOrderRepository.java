package com.electropro.electropos.repository;

import com.electropro.electropos.entity.PurchaseOrder;
import com.electropro.electropos.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    List<PurchaseOrder> findAllByStatus(String status);

    List<PurchaseOrder> findAllBySupplier(Supplier supplier);
}
