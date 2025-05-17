export interface Category {
  id: number;
  name: string;
  description?: string;
}

export interface Transaction {
  id: number;
  amount: number;
  date: string;
  description?: string;
  categoryId: number;
  categoryName: string;
  type: 'INCOME' | 'EXPENSE';
}

export interface Budget {
  id: number;
  name: string;
  amount: number;
  categoryId: number;
  categoryName: string;
  startDate: string;
  endDate: string;
}

export interface BudgetSummary {
  budgetId: number;
  budgetName: string;
  categoryName: string;
  budgetAmount: number;
  spentAmount: number;
  remainingAmount: number;
  spentPercentage: number;
}