package com.electropro.electropos.controller;

import com.electropro.electropos.dto.CategoryResponseDto;
import com.electropro.electropos.dto.ProductResponseDto;
import com.electropro.electropos.dto.SaleResponseDto;
import com.electropro.electropos.services.CategoryService;
import com.electropro.electropos.services.ProductService;
import com.electropro.electropos.services.SaleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    private final SaleService saleService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public ReportController(
            SaleService saleService,
            ProductService productService,
            CategoryService categoryService
    ) {
        this.saleService = saleService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/reports/daily")
    public List<SaleResponseDto> getDailyReport() {
        return saleService.findAllSales();
    }

    @GetMapping("/reports/category")
    public List<CategoryResponseDto> getCategoryReport() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/reports/stock")
    public List<ProductResponseDto> getStockReport() {
        return productService.findAllProducts();
    }
}
