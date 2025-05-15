package com.example.budgetmanagerio.repository;

import com.example.budgetmanagerio.model.Budget;
import com.example.budgetmanagerio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByCategory(Category category);

    @Query("SELECT b FROM Budget b WHERE " +
            "(:startDate BETWEEN b.startDate AND b.endDate OR " +
            ":endDate BETWEEN b.startDate AND b.endDate OR " +
            "b.startDate BETWEEN :startDate AND :endDate)")
    List<Budget> findByDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT b FROM Budget b WHERE " +
            "b.category.id = :categoryId AND " +
            "CURRENT_DATE BETWEEN b.startDate AND b.endDate")
    List<Budget> findActiveBudgetsByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT b FROM Budget b WHERE " +
            "CURRENT_DATE BETWEEN b.startDate AND b.endDate")
    List<Budget> findActiveBudgets();
    
}
