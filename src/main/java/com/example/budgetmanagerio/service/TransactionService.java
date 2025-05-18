package com.example.budgetmanagerio.service;


import com.example.budgetmanagerio.dto.TransactionDto;
import com.example.budgetmanagerio.exception.ResourceNotFoundException;
import com.example.budgetmanagerio.mapper.TransactionMapper;
import com.example.budgetmanagerio.model.Category;
import com.example.budgetmanagerio.model.Transaction;
import com.example.budgetmanagerio.repository.CategoryRepository;
import com.example.budgetmanagerio.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final TransactionMapper transactionMapper;

    @Transactional(readOnly = true)
    public Page<TransactionDto> getTransactions(
            Long categoryId, BigDecimal minAmount, BigDecimal maxAmount,
            LocalDateTime startDate, LocalDateTime endDate, Transaction.TransactionType type,
            Pageable pageable) {
        
        Page<Transaction> transactions = transactionRepository.findByFilters(
                categoryId, minAmount, maxAmount, startDate, endDate, type, pageable);
        
        return transactions.map(transactionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public TransactionDto getTransactionById(Long id) {
        Transaction transaction = findTransactionOrThrow(id);
        return transactionMapper.toDto(transaction);
    }

    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Category category = categoryService.findCategoryOrThrow(transactionDto.getCategoryId());
        
        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setCategory(category);
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(savedTransaction);
    }

    @Transactional
    public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
        Transaction existingTransaction = findTransactionOrThrow(id);
        

        if (!existingTransaction.getCategory().getId().equals(transactionDto.getCategoryId())) {
            Category category = categoryService.findCategoryOrThrow(transactionDto.getCategoryId());
            existingTransaction.setCategory(category);
        }
        
        transactionMapper.updateTransactionFromDto(transactionDto, existingTransaction);
        Transaction updatedTransaction = transactionRepository.save(existingTransaction);
        return transactionMapper.toDto(updatedTransaction);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private Transaction findTransactionOrThrow(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }
}