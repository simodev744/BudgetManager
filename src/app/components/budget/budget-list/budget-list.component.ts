import { Component, OnInit } from '@angular/core';
import {  Budget } from '../../../models';
import {Router, RouterLink} from '@angular/router';
import {ApiService} from '../../../services/api.service';
import {DatePipe, DecimalPipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-budget-list',
  templateUrl: './budget-list.component.html',
  imports: [
    RouterLink,
    NgForOf,
    DecimalPipe,
    DatePipe
  ],
  standalone: true
})
export class BudgetListComponent implements OnInit {
  budgets: Budget[] = [];
  loading = false;
  error = '';

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit(): void {
    this.loadBudgets();
  }

  loadBudgets(): void {
    this.loading = true;
    this.api.getBudgets().subscribe({
      next: (data) => {
        this.budgets = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load budgets.';
        this.loading = false;
      },
    });
  }

  deleteBudget(id: number): void {
    if (confirm('Are you sure you want to delete this budget?')) {
      this.api.deleteBudget(id).subscribe({
        next: () => {
          this.budgets = this.budgets.filter((b) => b.id !== id);
        },
        error: () => {
          alert('Failed to delete budget.');
        },
      });
    }
  }
}
