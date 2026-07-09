package com.electropro.electropos.services;

import com.electropro.electropos.dto.QuotationDto;
import com.electropro.electropos.dto.QuotationResponseDto;
import com.electropro.electropos.entity.*;
import com.electropro.electropos.mapper.QuotationMapper;
import com.electropro.electropos.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuotationService {

    private final QuotationRepository quotationRepository;
    private final QuotationMapper quotationMapper;
    private final QuotationItemRepository quotationItemRepository;
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;

    public QuotationService(QuotationRepository quotationRepository,
                            QuotationMapper quotationMapper,
                            QuotationItemRepository quotationItemRepository,
                            SaleRepository saleRepository,
                            SaleItemRepository saleItemRepository,
                            ProductRepository productRepository) {
        this.quotationRepository = quotationRepository;
        this.quotationMapper = quotationMapper;
        this.quotationItemRepository = quotationItemRepository;
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.productRepository = productRepository;
    }

    public QuotationResponseDto saveQuotation(QuotationDto dto) {
        var quotation = quotationMapper.toQuotation(dto);
        quotation.setQuotationDate(LocalDateTime.now());
        quotation.setStatus("DRAFT");
        var savedQuotation = quotationRepository.save(quotation);
        return quotationMapper.toQuotationResponseDto(savedQuotation);
    }

    public List<QuotationResponseDto> findAllQuotations() {
        return quotationRepository.findAll()
                .stream()
                .map(quotationMapper::toQuotationResponseDto)
                .collect(Collectors.toList());
    }

    public QuotationResponseDto findQuotationById(Integer id) {
        return quotationRepository.findById(id)
                .map(quotationMapper::toQuotationResponseDto)
                .orElse(null);
    }

    public QuotationResponseDto updateQuotation(Integer id, QuotationDto dto) {
        var existing = quotationRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setExpiryDate(dto.expiryDate());
        var updated = quotationRepository.save(existing);
        return quotationMapper.toQuotationResponseDto(updated);
    }

    /**
     * Converts a quotation into a real Sale: copies every quotation line into a
     * new sale (pricing preserved from the quotation), decrements stock for each
     * line, and links the quotation to the sale it produced. Idempotent — calling
     * convert twice on an already-converted quotation just returns the existing sale.
     */
    @Transactional
    public QuotationResponseDto convertQuotation(Integer id, String paymentMethod) {
        var quotation = quotationRepository.findById(id).orElse(null);
        if (quotation == null) return null;

        if ("CONVERTED".equals(quotation.getStatus()) && quotation.getConvertedSaleId() != null) {
            return quotationMapper.toQuotationResponseDto(quotation);
        }

        List<QuotationItem> quotationItems = quotationItemRepository.findByQuotationId(id);
        if (quotationItems.isEmpty()) {
            throw new IllegalStateException("Cannot convert a quotation with no line items");
        }

        Sale sale = new Sale();
        sale.setCustomer(quotation.getCustomer());
        sale.setSaleDate(LocalDateTime.now());
        sale.setReceiptNumber("RCP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        sale.setStatus("COMPLETED");
        sale.setPaymentMethod(paymentMethod != null ? paymentMethod : "UNSPECIFIED");
        Sale savedSale = saleRepository.save(sale);

        java.math.BigDecimal total = java.math.BigDecimal.ZERO;

        for (QuotationItem qi : quotationItems) {
            Product product = qi.getProduct();

            if (product != null) {
                int currentQty = product.getQuantity() == null ? 0 : product.getQuantity();
                if (currentQty < qi.getQuantity()) {
                    throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                }
                product.setQuantity(currentQty - qi.getQuantity());
                productRepository.save(product);
            }

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(savedSale);
            saleItem.setProduct(product);
            saleItem.setQuantity(qi.getQuantity());
            saleItem.setUnitPrice(qi.getUnitPrice());
            saleItem.setSubtotal(qi.getSubtotal());
            saleItemRepository.save(saleItem);

            total = total.add(qi.getSubtotal());
        }

        savedSale.setTotalAmount(total);
        saleRepository.save(savedSale);

        quotation.setStatus("CONVERTED");
        quotation.setConvertedSaleId(savedSale.getId());
        var updatedQuotation = quotationRepository.save(quotation);

        return quotationMapper.toQuotationResponseDto(updatedQuotation);
    }

    public void deleteQuotation(Integer id) {
        quotationRepository.deleteById(id);
    }
}