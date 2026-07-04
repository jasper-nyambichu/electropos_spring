package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.PurchaseOrderDto;
import com.electropro.electropos.dto.PurchaseOrderResponseDto;
import com.electropro.electropos.entity.PurchaseOrder;
import com.electropro.electropos.entity.Supplier;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderMapper {

    public PurchaseOrder toPurchaseOrder(PurchaseOrderDto dto) {
        var purchaseOrder = new PurchaseOrder();

        var supplier = new Supplier();
        supplier.setId(dto.supplierId());
        purchaseOrder.setSupplier(supplier);

        purchaseOrder.setStatus(dto.status());

        return purchaseOrder;
    }

    public PurchaseOrderResponseDto toPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        return new PurchaseOrderResponseDto(
                purchaseOrder.getId(),
                purchaseOrder.getOrderDate(),
                purchaseOrder.getTotalAmount(),
                purchaseOrder.getStatus(),
                purchaseOrder.getSupplier() != null ? purchaseOrder.getSupplier().getName() : null
        );
    }
}