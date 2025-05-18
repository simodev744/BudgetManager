import { Component, OnInit } from '@angular/core';
import { Category, Transaction, Budget, BudgetSummary } from '../../models';
import {ApiService} from '../../services/api.service';
import {DecimalPipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  imports: [
    DecimalPipe,
    NgForOf
  ],
  standalone: true
})
export class DashboardComponent implements OnInit {
  categoryCount = 0;
  transactionCount = 0;
  budgetCount = 0;
  budgetSummaries: BudgetSummary[] = [];

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.api.getCategories().subscribe(cats => (this.categoryCount = cats.length));
    this.api.getTransactions().subscribe(trans => (this.transactionCount = trans.length));
    this.api.getBudgets().subscribe(budgets => (this.budgetCount = budgets.length));
    this.api.getBudgetSummaries().subscribe(summaries => (this.budgetSummaries = summaries));
  }
}
