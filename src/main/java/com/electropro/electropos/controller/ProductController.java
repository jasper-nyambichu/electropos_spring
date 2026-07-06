package com.electropro.electropos.controller;

import com.electropro.electropos.dto.ProductDto;
import com.electropro.electropos.dto.ProductResponseDto;
import com.electropro.electropos.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ProductResponseDto saveProduct(
            @RequestBody ProductDto dto
    ) {
        return productService.saveProduct(dto);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/products/{product-id}")
    public ProductResponseDto findProductById(
            @PathVariable("product-id") Integer id
    ) {
        return productService.findProductById(id);
    }

    @GetMapping("/products/search/{product-name}")
    public List<ProductResponseDto> findProductsByName(
            @PathVariable("product-name") String name
    ) {
        return productService.findProductsByName(name);
    }

    @GetMapping("/products/low-stock")
    public List<ProductResponseDto> findLowStockProducts() {
        return productService.findLowStockProducts();
    }

    @PutMapping("/products/{product-id}")
    public ProductResponseDto updateProduct(
            @PathVariable("product-id") Integer id,
            @RequestBody ProductDto dto
    ) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping("/products/{product-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(
            @PathVariable("product-id") Integer id
    ) {
        productService.deleteProduct(id);
    }
}