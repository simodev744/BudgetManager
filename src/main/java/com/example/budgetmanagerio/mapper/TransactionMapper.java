package com.example.budgetmanagerio.mapper;


import com.example.budgetmanagerio.dto.TransactionDto;
import com.example.budgetmanagerio.model.Category;
import com.example.budgetmanagerio.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    TransactionDto toDto(Transaction transaction);
    
    List<TransactionDto> toDtoList(List<Transaction> transactions);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryFromId")
    Transaction toEntity(TransactionDto transactionDto);
    
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryFromId")
    void updateTransactionFromDto(TransactionDto dto, @MappingTarget Transaction transaction);
    
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