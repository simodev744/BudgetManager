package com.example.budgetmanagerio.mapper;

import com.example.budgetmanagerio.dto.CategoryDto;
import com.example.budgetmanagerio.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    CategoryDto toDto(Optional<Category> category);
    
    List<CategoryDto> toDtoList(List<Category> categories);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "budgets", ignore = true)
    Category toEntity(CategoryDto categoryDto);
    
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "budgets", ignore = true)
    void updateCategoryFromDto(CategoryDto dto, @MappingTarget Category category);
}