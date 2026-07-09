package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.QuotationItemDto;
import com.electropro.electropos.dto.QuotationItemResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.Quotation;
import com.electropro.electropos.entity.QuotationItem;
import org.springframework.stereotype.Service;

@Service
public class QuotationItemMapper {

    public QuotationItem toQuotationItem(QuotationItemDto dto) {
        var quotationItem = new QuotationItem();

        var quotation = new Quotation();
        quotation.setId(dto.quotationId());
        quotationItem.setQuotation(quotation);

        var product = new Product();
        product.setId(dto.productId());
        quotationItem.setProduct(product);

        quotationItem.setQuantity(dto.quantity());
        quotationItem.setUnitPrice(dto.unitPrice());

        return quotationItem;
    }

    public QuotationItemResponseDto toQuotationItemResponseDto(QuotationItem quotationItem) {
        return new QuotationItemResponseDto(
                quotationItem.getId(),
                quotationItem.getQuotation() != null ? quotationItem.getQuotation().getId() : null,
                quotationItem.getProduct() != null ? quotationItem.getProduct().getId() : null,
                quotationItem.getProduct() != null ? quotationItem.getProduct().getName() : null,
                quotationItem.getQuantity(),
                quotationItem.getUnitPrice(),
                quotationItem.getSubtotal()
        );
    }
}