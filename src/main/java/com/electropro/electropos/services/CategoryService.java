package com.electropro.electropos.services;

import com.electropro.electropos.dto.CategoryDto;
import com.electropro.electropos.dto.CategoryResponseDto;
import com.electropro.electropos.mapper.CategoryMapper;
import com.electropro.electropos.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponseDto saveCategory(CategoryDto dto) {
        var category = categoryMapper.toCategory(dto);
        var savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public List<CategoryResponseDto> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto findCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toCategoryResponseDto)
                .orElse(null);
    }

    public CategoryResponseDto updateCategory(Integer id, CategoryDto dto) {
        var existing = categoryRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setName(dto.name());
        existing.setDescription(dto.description());
        var updated = categoryRepository.save(existing);
        return categoryMapper.toCategoryResponseDto(updated);
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}