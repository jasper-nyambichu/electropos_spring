package com.electropro.electropos.services;

import com.electropro.electropos.dto.PurchaseOrderItemDto;
import com.electropro.electropos.dto.PurchaseOrderItemResponseDto;
import com.electropro.electropos.mapper.PurchaseOrderItemMapper;
import com.electropro.electropos.repository.PurchaseOrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderItemService {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;

    public PurchaseOrderItemService(PurchaseOrderItemRepository purchaseOrderItemRepository, PurchaseOrderItemMapper purchaseOrderItemMapper) {
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.purchaseOrderItemMapper = purchaseOrderItemMapper;
    }

    public PurchaseOrderItemResponseDto savePurchaseOrderItem(PurchaseOrderItemDto dto) {
        var purchaseOrderItem = purchaseOrderItemMapper.toPurchaseOrderItem(dto);
        var savedItem = purchaseOrderItemRepository.save(purchaseOrderItem);
        return purchaseOrderItemMapper.toPurchaseOrderItemResponseDto(savedItem);
    }

    public List<PurchaseOrderItemResponseDto> findAllPurchaseOrderItems() {
        return purchaseOrderItemRepository.findAll()
                .stream()
                .map(purchaseOrderItemMapper::toPurchaseOrderItemResponseDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderItemResponseDto findPurchaseOrderItemById(Integer id) {
        return purchaseOrderItemRepository.findById(id)
                .map(purchaseOrderItemMapper::toPurchaseOrderItemResponseDto)
                .orElse(null);
    }
}