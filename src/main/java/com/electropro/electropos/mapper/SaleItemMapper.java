package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.SaleItemDto;
import com.electropro.electropos.dto.SaleItemResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.Sale;
import com.electropro.electropos.entity.SaleItem;
import org.springframework.stereotype.Service;

@Service
public class SaleItemMapper {

    public SaleItem toSaleItem(SaleItemDto dto) {
        var saleItem = new SaleItem();

        var sale = new Sale();
        sale.setId(dto.saleId());
        saleItem.setSale(sale);

        var product = new Product();
        product.setId(dto.productId());
        saleItem.setProduct(product);

        saleItem.setQuantity(dto.quantity());

        return saleItem;
    }

    public SaleItemResponseDto toSaleItemResponseDto(SaleItem saleItem) {
        return new SaleItemResponseDto(
                saleItem.getId(),
                saleItem.getSale() != null ? saleItem.getSale().getId() : null,
                saleItem.getProduct() != null ? saleItem.getProduct().getId() : null,
                saleItem.getProduct() != null ? saleItem.getProduct().getName() : null,
                saleItem.getQuantity(),
                saleItem.getUnitPrice(),
                saleItem.getSubtotal()
        );
    }
}