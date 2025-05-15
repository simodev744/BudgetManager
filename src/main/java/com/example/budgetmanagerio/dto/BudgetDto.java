package com.example.budgetmanagerio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetDto {
    private Long id;
    
    @NotBlank(message = "Budget name cannot be empty")
    private String name;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
    
    private String categoryName;
    
    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;
    
    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
}