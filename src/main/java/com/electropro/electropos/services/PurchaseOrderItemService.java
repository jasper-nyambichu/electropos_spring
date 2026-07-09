package com.electropro.electropos.services;

import com.electropro.electropos.dto.PurchaseOrderItemDto;
import com.electropro.electropos.dto.PurchaseOrderItemResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.PurchaseOrder;
import com.electropro.electropos.entity.PurchaseOrderItem;
import com.electropro.electropos.mapper.PurchaseOrderItemMapper;
import com.electropro.electropos.repository.ProductRepository;
import com.electropro.electropos.repository.PurchaseOrderItemRepository;
import com.electropro.electropos.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderItemService {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final PurchaseOrderItemMapper purchaseOrderItemMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderItemService(PurchaseOrderItemRepository purchaseOrderItemRepository,
                                    PurchaseOrderItemMapper purchaseOrderItemMapper,
                                    PurchaseOrderRepository purchaseOrderRepository,
                                    ProductRepository productRepository) {
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.purchaseOrderItemMapper = purchaseOrderItemMapper;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
    }

    /**
     * Adds a line item to a purchase order and recomputes the order's totalAmount.
     * Stock is intentionally NOT touched here — it's applied when the order is received.
     */
    @Transactional
    public PurchaseOrderItemResponseDto savePurchaseOrderItem(PurchaseOrderItemDto dto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(dto.purchaseOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Purchase order not found: " + dto.purchaseOrderId()));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

        PurchaseOrderItem item = new PurchaseOrderItem();
        item.setPurchaseOrder(purchaseOrder);
        item.setProduct(product);
        item.setQuantity(dto.quantity());
        item.setUnitCost(dto.unitCost());
        item.setSubtotal(dto.unitCost().multiply(BigDecimal.valueOf(dto.quantity())));

        PurchaseOrderItem saved = purchaseOrderItemRepository.save(item);

        recomputePurchaseOrderTotal(purchaseOrder.getId());

        return purchaseOrderItemMapper.toPurchaseOrderItemResponseDto(saved);
    }

    @Transactional
    public void deletePurchaseOrderItem(Integer id) {
        PurchaseOrderItem item = purchaseOrderItemRepository.findById(id).orElse(null);
        if (item == null) return;

        Integer orderId = item.getPurchaseOrder() != null ? item.getPurchaseOrder().getId() : null;
        purchaseOrderItemRepository.deleteById(id);

        if (orderId != null) {
            recomputePurchaseOrderTotal(orderId);
        }
    }

    void recomputePurchaseOrderTotal(Integer purchaseOrderId) {
        BigDecimal total = purchaseOrderItemRepository.findByPurchaseOrderId(purchaseOrderId).stream()
                .map(PurchaseOrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        purchaseOrderRepository.findById(purchaseOrderId).ifPresent(po -> {
            po.setTotalAmount(total);
            purchaseOrderRepository.save(po);
        });
    }

    public List<PurchaseOrderItemResponseDto> findAllPurchaseOrderItems() {
        return purchaseOrderItemRepository.findAll()
                .stream()
                .map(purchaseOrderItemMapper::toPurchaseOrderItemResponseDto)
                .collect(Collectors.toList());
    }

    public List<PurchaseOrderItemResponseDto> findItemsByPurchaseOrderId(Integer purchaseOrderId) {
        return purchaseOrderItemRepository.findByPurchaseOrderId(purchaseOrderId)
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