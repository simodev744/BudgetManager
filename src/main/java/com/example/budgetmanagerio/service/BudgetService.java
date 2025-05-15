package com.example.budgetmanagerio.service;

import com.example.budgetmanagerio.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    //private final TransactionRepository transactionRepository;
    //private final CategoryService categoryService;

}
