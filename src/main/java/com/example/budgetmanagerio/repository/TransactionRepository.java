package com.example.budgetmanagerio.repository;


import com.example.budgetmanagerio.model.Category;
import com.example.budgetmanagerio.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByCategory(Category category, Pageable pageable);
    
    Page<Transaction> findByAmountBetween(BigDecimal min, BigDecimal max, Pageable pageable);
    
    Page<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:categoryId IS NULL OR t.category.id = :categoryId) AND " +
           "(:minAmount IS NULL OR t.amount >= :minAmount) AND " +
           "(:maxAmount IS NULL OR t.amount <= :maxAmount) AND " +
           "(:startDate IS NULL OR t.date >= :startDate) AND " +
           "(:endDate IS NULL OR t.date <= :endDate) AND " +
           "(:type IS NULL OR t.type = :type)")
    Page<Transaction> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("type") Transaction.TransactionType type,
            Pageable pageable);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE " +
           "t.category.id = :categoryId AND " +
           "t.type = 'EXPENSE' AND " +
           "t.date BETWEEN :startDate AND :endDate")
    BigDecimal sumExpensesByCategoryAndDateRange(
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

}