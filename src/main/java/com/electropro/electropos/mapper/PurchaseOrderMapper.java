package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.PurchaseOrderDto;
import com.electropro.electropos.dto.PurchaseOrderResponseDto;
import com.electropro.electropos.entity.PurchaseOrder;
import com.electropro.electropos.entity.Supplier;
import com.electropro.electropos.repository.PurchaseOrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderMapper {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;

    public PurchaseOrderMapper(PurchaseOrderItemRepository purchaseOrderItemRepository,
                               PurchaseOrderItemMapper purchaseOrderItemMapper) {
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.purchaseOrderItemMapper = purchaseOrderItemMapper;
    }

    public PurchaseOrder toPurchaseOrder(PurchaseOrderDto dto) {
        var purchaseOrder = new PurchaseOrder();

        var supplier = new Supplier();
        supplier.setId(dto.supplierId());
        purchaseOrder.setSupplier(supplier);

        purchaseOrder.setStatus(dto.status());

        return purchaseOrder;
    }

    public PurchaseOrderResponseDto toPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        List<com.electropro.electropos.dto.PurchaseOrderItemResponseDto> items = purchaseOrder.getId() == null
                ? Collections.emptyList()
                : purchaseOrderItemRepository.findByPurchaseOrderId(purchaseOrder.getId()).stream()
                .map(purchaseOrderItemMapper::toPurchaseOrderItemResponseDto)
                .collect(Collectors.toList());

        return new PurchaseOrderResponseDto(
                purchaseOrder.getId(),
                purchaseOrder.getOrderDate(),
                purchaseOrder.getTotalAmount(),
                purchaseOrder.getStatus(),
                purchaseOrder.getSupplier() != null ? purchaseOrder.getSupplier().getName() : null,
                items
        );
    }
}