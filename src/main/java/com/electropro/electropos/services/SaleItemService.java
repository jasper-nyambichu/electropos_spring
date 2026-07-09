package com.electropro.electropos.services;

import com.electropro.electropos.dto.SaleItemDto;
import com.electropro.electropos.dto.SaleItemResponseDto;
import com.electropro.electropos.entity.Product;
import com.electropro.electropos.entity.Sale;
import com.electropro.electropos.entity.SaleItem;
import com.electropro.electropos.mapper.SaleItemMapper;
import com.electropro.electropos.repository.ProductRepository;
import com.electropro.electropos.repository.SaleItemRepository;
import com.electropro.electropos.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleItemService {

    private final SaleItemRepository saleItemRepository;
    private final SaleItemMapper saleItemMapper;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleItemService(SaleItemRepository saleItemRepository,
                           SaleItemMapper saleItemMapper,
                           SaleRepository saleRepository,
                           ProductRepository productRepository) {
        this.saleItemRepository = saleItemRepository;
        this.saleItemMapper = saleItemMapper;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    /**
     * Adds a line item to a sale: prices it off the live product price,
     * decrements stock, and recomputes the parent sale's totalAmount.
     */
    @Transactional
    public SaleItemResponseDto saveSaleItem(SaleItemDto dto) {
        Sale sale = saleRepository.findById(dto.saleId())
                .orElseThrow(() -> new IllegalArgumentException("Sale not found: " + dto.saleId()));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + dto.productId()));

        if (product.getQuantity() == null || product.getQuantity() < dto.quantity()) {
            throw new IllegalStateException("Insufficient stock for product: " + product.getName());
        }

        SaleItem saleItem = new SaleItem();
        saleItem.setSale(sale);
        saleItem.setProduct(product);
        saleItem.setQuantity(dto.quantity());
        saleItem.setUnitPrice(product.getPrice());
        saleItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(dto.quantity())));

        SaleItem saved = saleItemRepository.save(saleItem);

        product.setQuantity(product.getQuantity() - dto.quantity());
        productRepository.save(product);

        recomputeSaleTotal(sale.getId());

        return saleItemMapper.toSaleItemResponseDto(saved);
    }

    @Transactional
    public void deleteSaleItem(Integer id) {
        SaleItem item = saleItemRepository.findById(id).orElse(null);
        if (item == null) return;

        // restore stock since this line is being voided
        Product product = item.getProduct();
        if (product != null) {
            product.setQuantity((product.getQuantity() == null ? 0 : product.getQuantity()) + item.getQuantity());
            productRepository.save(product);
        }

        Integer saleId = item.getSale() != null ? item.getSale().getId() : null;
        saleItemRepository.deleteById(id);

        if (saleId != null) {
            recomputeSaleTotal(saleId);
        }
    }

    private void recomputeSaleTotal(Integer saleId) {
        BigDecimal total = saleItemRepository.findBySaleId(saleId).stream()
                .map(SaleItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        saleRepository.findById(saleId).ifPresent(sale -> {
            sale.setTotalAmount(total);
            saleRepository.save(sale);
        });
    }

    public List<SaleItemResponseDto> findAllSaleItems() {
        return saleItemRepository.findAll()
                .stream()
                .map(saleItemMapper::toSaleItemResponseDto)
                .collect(Collectors.toList());
    }

    public List<SaleItemResponseDto> findSaleItemsBySaleId(Integer saleId) {
        return saleItemRepository.findBySaleId(saleId)
                .stream()
                .map(saleItemMapper::toSaleItemResponseDto)
                .collect(Collectors.toList());
    }

    public SaleItemResponseDto findSaleItemById(Integer id) {
        return saleItemRepository.findById(id)
                .map(saleItemMapper::toSaleItemResponseDto)
                .orElse(null);
    }
}