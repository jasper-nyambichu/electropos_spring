package com.electropro.electropos.services;

import com.electropro.electropos.dto.PurchaseOrderDto;
import com.electropro.electropos.dto.PurchaseOrderResponseDto;
import com.electropro.electropos.mapper.PurchaseOrderMapper;
import com.electropro.electropos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    public PurchaseOrderResponseDto savePurchaseOrder(PurchaseOrderDto dto) {
        var purchaseOrder = purchaseOrderMapper.toPurchaseOrder(dto);
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setStatus("PENDING");
        var savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return purchaseOrderMapper.toPurchaseOrderResponseDto(savedPurchaseOrder);
    }

    public List<PurchaseOrderResponseDto> findAllPurchaseOrders() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(purchaseOrderMapper::toPurchaseOrderResponseDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderResponseDto findPurchaseOrderById(Integer id) {
        return purchaseOrderRepository.findById(id)
                .map(purchaseOrderMapper::toPurchaseOrderResponseDto)
                .orElse(null);
    }

    public PurchaseOrderResponseDto receivePurchaseOrder(Integer id) {
        var existing = purchaseOrderRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setStatus("RECEIVED");
        var updated = purchaseOrderRepository.save(existing);
        return purchaseOrderMapper.toPurchaseOrderResponseDto(updated);
    }

    public void deletePurchaseOrder(Integer id) {
        purchaseOrderRepository.deleteById(id);
    }
}