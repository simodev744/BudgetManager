package com.example.budgetmanagerio.controller;


import com.example.budgetmanagerio.dto.BudgetDto;
import com.example.budgetmanagerio.dto.BudgetSummaryDto;
import com.example.budgetmanagerio.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetDto>> getAllBudgets() {

        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    @GetMapping("/active")
    public ResponseEntity<List<BudgetDto>> getActiveBudgets() {
        return ResponseEntity.ok(budgetService.getActiveBudgets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetDto> getBudgetById(@PathVariable Long id) {
        return ResponseEntity.ok(budgetService.getBudgetById(id));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<BudgetSummaryDto>> getBudgetSummaries() {
        return ResponseEntity.ok(budgetService.getBudgetSummaries());
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<BudgetSummaryDto> getBudgetSummary(@PathVariable Long id) {
        return ResponseEntity.ok(budgetService.getBudgetSummary(id));
    }

    @PostMapping
    public ResponseEntity<BudgetDto> createBudget(@Valid @RequestBody BudgetDto budgetDto) {
        BudgetDto createdBudget = budgetService.createBudget(budgetDto);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDto> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetDto budgetDto) {
        return ResponseEntity.ok(budgetService.updateBudget(id, budgetDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }

}