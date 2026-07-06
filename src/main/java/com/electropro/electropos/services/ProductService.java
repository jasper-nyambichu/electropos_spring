package com.electropro.electropos.services;

import com.electropro.electropos.dto.ProductDto;
import com.electropro.electropos.dto.ProductResponseDto;
import com.electropro.electropos.mapper.ProductMapper;
import com.electropro.electropos.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDto saveProduct(ProductDto dto) {
        var product = productMapper.toProduct(dto);
        var savedProduct = productRepository.save(product);
        return productMapper.toProductResponseDto(savedProduct);
    }

    public List<ProductResponseDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto findProductById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponseDto)
                .orElse(null);
    }

    public List<ProductResponseDto> findProductsByName(String name) {
        return productRepository.findAllByNameContaining(name)
                .stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> findLowStockProducts() {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getQuantity() <= p.getLowStockThreshold())
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto updateProduct(Integer id, ProductDto dto) {
        var existing = productRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setName(dto.name());
        existing.setSku(dto.sku());
        existing.setPrice(dto.price());
        existing.setCostPrice(dto.costPrice());
        existing.setQuantity(dto.quantity());
        existing.setLowStockThreshold(dto.lowStockThreshold());
        existing.setBarcode(dto.barcode());
        existing.setImageUrl(dto.imageUrl());
        var updated = productRepository.save(existing);
        return productMapper.toProductResponseDto(updated);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}