package com.electropro.electropos.mapper;

import com.electropro.electropos.dto.CategoryDto;
import com.electropro.electropos.dto.CategoryResponseDto;
import com.electropro.electropos.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
public Category toCategory(CategoryDto dto){
    return new Category(dto.name(), dto.description());
}

public CategoryResponseDto toCategoryResponseDto(Category category){
    return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.getDescription());
}
}
