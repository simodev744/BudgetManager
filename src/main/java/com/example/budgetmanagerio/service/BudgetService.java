package com.example.budgetmanagerio.service;


import com.example.budgetmanagerio.dto.BudgetDto;
import com.example.budgetmanagerio.dto.BudgetSummaryDto;
import com.example.budgetmanagerio.exception.ResourceNotFoundException;
import com.example.budgetmanagerio.mapper.BudgetMapper;
import com.example.budgetmanagerio.model.Budget;
import com.example.budgetmanagerio.model.Category;
import com.example.budgetmanagerio.repository.BudgetRepository;
import com.example.budgetmanagerio.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private BudgetMapper budgetMapper;

    @Transactional(readOnly = true)
    public List<BudgetDto> getAllBudgets() {
        List<Budget> budgets = budgetRepository.findAll();
        return budgetMapper.toDtoList(budgets);
    }

    @Transactional(readOnly = true)
    public List<BudgetDto> getActiveBudgets() {
        List<Budget> activeBudgets = budgetRepository.findActiveBudgets();
        return budgetMapper.toDtoList(activeBudgets);
    }

    @Transactional(readOnly = true)
    public BudgetDto getBudgetById(Long id) {
        Budget budget = findBudgetOrThrow(id);
        return budgetMapper.toDto(budget);
    }

    @Transactional
    public BudgetDto createBudget(BudgetDto budgetDto) {

        Category category = categoryService.findCategoryOrThrow(budgetDto.getCategoryId());

        Budget budget = budgetMapper.toEntity(budgetDto);
        budget.setCategory(category);
        
        Budget savedBudget = budgetRepository.save(budget);
        return budgetMapper.toDto(savedBudget);
    }

    @Transactional
    public BudgetDto updateBudget(Long id, BudgetDto budgetDto) {
        Budget existingBudget = findBudgetOrThrow(id);
        
        if (!existingBudget.getCategory().getId().equals(budgetDto.getCategoryId())) {
            Category category = categoryService.findCategoryOrThrow(budgetDto.getCategoryId());
            existingBudget.setCategory(category);
        }
        
        budgetMapper.updateBudgetFromDto(budgetDto, existingBudget);
        Budget updatedBudget = budgetRepository.save(existingBudget);
        return budgetMapper.toDto(updatedBudget);
    }

    @Transactional
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<BudgetSummaryDto> getBudgetSummaries() {
        List<Budget> activeBudgets = budgetRepository.findActiveBudgets();

        return activeBudgets.stream()
                .map(this::createBudgetSummary)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BudgetSummaryDto getBudgetSummary(Long budgetId) {
        Budget budget = findBudgetOrThrow(budgetId);
        return createBudgetSummary(budget);
    }

    private BudgetSummaryDto createBudgetSummary(Budget budget) {
        LocalDateTime startDateTime = budget.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = budget.getEndDate().atTime(LocalTime.MAX);

        BigDecimal spentAmount = transactionRepository.sumExpensesByCategoryAndDateRange(
                budget.getCategory().getId(), startDateTime, endDateTime);

        // If no expenses, set to zero
        if (spentAmount == null) {
            spentAmount = BigDecimal.ZERO;
        }

        BigDecimal remainingAmount = budget.getAmount().subtract(spentAmount);
        double spentPercentage = 0.0;

        if (budget.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            spentPercentage = spentAmount.divide(budget.getAmount(), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();
        }

        return BudgetSummaryDto.builder()
                .budgetId(budget.getId())
                .budgetName(budget.getName())
                .categoryName(budget.getCategory().getName())
                .budgetAmount(budget.getAmount())
                .spentAmount(spentAmount)
                .remainingAmount(remainingAmount)
                .spentPercentage(spentPercentage)
                .build();
    }

    private Budget findBudgetOrThrow(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
    }
}