package com.electropro.electropos.services;

import com.electropro.electropos.dto.PurchaseOrderDto;
import com.electropro.electropos.dto.PurchaseOrderResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.PurchaseOrderItem;
import com.electropro.electropos.mapper.PurchaseOrderMapper;
import com.electropro.electropos.repository.ProductRepository;
import com.electropro.electropos.repository.PurchaseOrderItemRepository;
import com.electropro.electropos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                PurchaseOrderMapper purchaseOrderMapper,
                                PurchaseOrderItemRepository purchaseOrderItemRepository,
                                ProductRepository productRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.productRepository = productRepository;
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

    /**
     * Marks the order RECEIVED and increments stock for every line item.
     * Guarded so it can't be applied twice (which would double-count stock).
     */
    @Transactional
    public PurchaseOrderResponseDto receivePurchaseOrder(Integer id) {
        var existing = purchaseOrderRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if ("RECEIVED".equals(existing.getStatus())) {
            // already received — don't double-apply stock
            return purchaseOrderMapper.toPurchaseOrderResponseDto(existing);
        }

        List<PurchaseOrderItem> items = purchaseOrderItemRepository.findByPurchaseOrderId(id);
        for (PurchaseOrderItem item : items) {
            Product product = item.getProduct();
            if (product == null) continue;
            int currentQty = product.getQuantity() == null ? 0 : product.getQuantity();
            product.setQuantity(currentQty + item.getQuantity());
            productRepository.save(product);
        }

        existing.setStatus("RECEIVED");
        var updated = purchaseOrderRepository.save(existing);
        return purchaseOrderMapper.toPurchaseOrderResponseDto(updated);
    }

    public void deletePurchaseOrder(Integer id) {
        purchaseOrderRepository.deleteById(id);
    }
}