package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.ProductDto;
import com.electropro.electropos.dto.ProductResponseDto;
import com.electropro.electropos.entity.Category;
import com.electropro.electropos.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductDto dto) {
        var product = new Product(
                dto.name(),
                dto.sku(),
                dto.price(),
                dto.costPrice(),
                dto.quantity(),
                dto.lowStockThreshold(),
                dto.barcode(),
                dto.imageUrl()
        );

        var category = new Category();
        category.setId(dto.categoryId());
        product.setCategory(category);

        return product;
    }

    public ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getQuantity(),
                product.getLowStockThreshold(),
                product.getBarcode(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
