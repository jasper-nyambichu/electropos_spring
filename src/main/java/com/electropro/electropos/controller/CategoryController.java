package com.electropro.electropos.controller;

import com.electropro.electropos.dto.CategoryDto;
import com.electropro.electropos.dto.CategoryResponseDto;
import com.electropro.electropos.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public CategoryResponseDto saveCategory(
            @RequestBody CategoryDto dto
    ) {
        return categoryService.saveCategory(dto);
    }

    @GetMapping("/categories")
    public List<CategoryResponseDto> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/categories/{category-id}")
    public CategoryResponseDto findCategoryById(
            @PathVariable("category-id") Integer id
    ) {
        return categoryService.findCategoryById(id);
    }

    @PutMapping("/categories/{category-id}")
    public CategoryResponseDto updateCategory(
            @PathVariable("category-id") Integer id,
            @RequestBody CategoryDto dto
    ) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/categories/{category-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(
            @PathVariable("category-id") Integer id
    ) {
        categoryService.deleteCategory(id);
    }
}