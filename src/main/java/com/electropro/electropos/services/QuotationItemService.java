package com.electropro.electropos.services;

import com.electropro.electropos.dto.QuotationItemDto;
import com.electropro.electropos.dto.QuotationItemResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.Quotation;
import com.electropro.electropos.entity.QuotationItem;
import com.electropro.electropos.mapper.QuotationItemMapper;
import com.electropro.electropos.repository.ProductRepository;
import com.electropro.electropos.repository.QuotationItemRepository;
import com.electropro.electropos.repository.QuotationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotationItemService {

    private final QuotationItemRepository quotationItemRepository;
    private final QuotationItemMapper quotationItemMapper;
    private final QuotationRepository quotationRepository;
    private final ProductRepository productRepository;

    public QuotationItemService(QuotationItemRepository quotationItemRepository,
                                QuotationItemMapper quotationItemMapper,
                                QuotationRepository quotationRepository,
                                ProductRepository productRepository) {
        this.quotationItemRepository = quotationItemRepository;
        this.quotationItemMapper = quotationItemMapper;
        this.quotationRepository = quotationRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public QuotationItemResponseDto saveQuotationItem(QuotationItemDto dto) {
        Quotation quotation = quotationRepository.findById(dto.quotationId())
                .orElseThrow(() -> new IllegalArgumentException("Quotation not found: " + dto.quotationId()));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

        // quotations can offer custom pricing; fall back to the product's list price if none given
        BigDecimal unitPrice = dto.unitPrice() != null ? dto.unitPrice() : product.getPrice();

        QuotationItem item = new QuotationItem();
        item.setQuotation(quotation);
        item.setProduct(product);
        item.setQuantity(dto.quantity());
        item.setUnitPrice(unitPrice);
        item.setSubtotal(unitPrice.multiply(BigDecimal.valueOf(dto.quantity())));

        QuotationItem saved = quotationItemRepository.save(item);

        recomputeQuotationTotal(quotation.getId());

        return quotationItemMapper.toQuotationItemResponseDto(saved);
    }

    @Transactional
    public void deleteQuotationItem(Integer id) {
        QuotationItem item = quotationItemRepository.findById(id).orElse(null);
        if (item == null) return;

        Integer quotationId = item.getQuotation() != null ? item.getQuotation().getId() : null;
        quotationItemRepository.deleteById(id);

        if (quotationId != null) {
            recomputeQuotationTotal(quotationId);
        }
    }

    void recomputeQuotationTotal(Integer quotationId) {
        BigDecimal total = quotationItemRepository.findByQuotationId(quotationId).stream()
                .map(QuotationItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        quotationRepository.findById(quotationId).ifPresent(q -> {
            q.setTotalAmount(total);
            quotationRepository.save(q);
        });
    }

    public List<QuotationItemResponseDto> findAllQuotationItems() {
        return quotationItemRepository.findAll()
                .stream()
                .map(quotationItemMapper::toQuotationItemResponseDto)
                .collect(Collectors.toList());
    }

    public List<QuotationItemResponseDto> findItemsByQuotationId(Integer quotationId) {
        return quotationItemRepository.findByQuotationId(quotationId)
                .stream()
                .map(quotationItemMapper::toQuotationItemResponseDto)
                .collect(Collectors.toList());
    }

    public QuotationItemResponseDto findQuotationItemById(Integer id) {
        return quotationItemRepository.findById(id)
                .map(quotationItemMapper::toQuotationItemResponseDto)
                .orElse(null);
    }
}