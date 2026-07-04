package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.PurchaseOrderItemDto;
import com.electropro.electropos.dto.PurchaseOrderItemResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.PurchaseOrder;
import com.electropro.electropos.entity.PurchaseOrderItem;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderItemMapper {

    public PurchaseOrderItem toPurchaseOrderItem(PurchaseOrderItemDto dto) {
        var purchaseOrderItem = new PurchaseOrderItem();

        var purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(dto.purchaseOrderId());
        purchaseOrderItem.setPurchaseOrder(purchaseOrder);

        var product = new Product();
        product.setId(dto.productId());
        purchaseOrderItem.setProduct(product);

        purchaseOrderItem.setQuantity(dto.quantity());
        purchaseOrderItem.setUnitCost(dto.unitCost());

        return purchaseOrderItem;
    }

    public PurchaseOrderItemResponseDto toPurchaseOrderItemResponseDto(PurchaseOrderItem purchaseOrderItem) {
        return new PurchaseOrderItemResponseDto(
                purchaseOrderItem.getProduct() != null ? purchaseOrderItem.getProduct().getName() : null,
                purchaseOrderItem.getQuantity(),
                purchaseOrderItem.getUnitCost(),
                purchaseOrderItem.getSubtotal()
        );
    }
}