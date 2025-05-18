import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {  Budget, Category } from '../../../models';
import {ApiService} from '../../../services/api.service';
import {FormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-budget-form',
  templateUrl: './budget-form.component.html',
  imports: [
    FormsModule,
    NgForOf
  ],
  standalone: true
})
export class BudgetFormComponent implements OnInit {
  budget: Budget = {
    id: 0,
    name: '',
    amount: 0,
    categoryId: 0,
    categoryName: '',
    startDate: '',
    endDate: '',
  };

  categories: Category[] = [];
  isEdit = false;
  loading = false;
  error = '';

  constructor(
    private api: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.api.getCategories().subscribe((cats) => (this.categories = cats));

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loading = true;
      this.api.getBudget(+id).subscribe({
        next: (budget) => {
          this.budget = budget;
          this.loading = false;
        },
        error: () => {
          this.error = 'Failed to load budget.';
          this.loading = false;
        },
      });
    }
  }

  save(): void {
    if (this.isEdit) {
      this.api.updateBudget(this.budget.id, this.budget).subscribe({
        next: () => this.router.navigate(['/budgets']),
        error: () => alert('Failed to update budget.'),
      });
    } else {
      this.api.createBudget(this.budget).subscribe({
        next: () => this.router.navigate(['/budgets']),
        error: () => alert('Failed to create budget.'),
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/budgets']);
  }
}
