package com.example.budgetmanagerio.dto;


import com.example.budgetmanagerio.model.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    private String description;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private String categoryName;

    @NotNull(message = "Transaction type cannot be null")
    private Transaction.TransactionType type;
}