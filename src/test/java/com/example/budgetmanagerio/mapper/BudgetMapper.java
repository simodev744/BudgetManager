package com.example.budgetmanagerio.mapper;

import com.example.budgetmanagerio.dto.BudgetDto;
import com.example.budgetmanagerio.model.Budget;
import com.example.budgetmanagerio.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    BudgetDto toDto(Budget budget);
    
    List<BudgetDto> toDtoList(List<Budget> budgets);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryFromId")
    Budget toEntity(BudgetDto budgetDto);
    
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryFromId")
    void updateBudgetFromDto(BudgetDto dto, @MappingTarget Budget budget);
    
    @Named("categoryFromId")
    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}