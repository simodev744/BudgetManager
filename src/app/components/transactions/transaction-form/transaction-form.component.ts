import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {  Transaction, Category } from '../../../models';
import {ApiService} from '../../../services/api.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-transaction-form',
  templateUrl: './transaction-form.component.html',
  imports: [
    FormsModule
  ],
  standalone: true
})
export class TransactionFormComponent implements OnInit {
  transaction: Partial<Transaction> = { type: 'EXPENSE' };
  categories: Category[] = [];
  isEdit = false;

  constructor(
    private api: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.api.getCategories().subscribe(cats => (this.categories = cats));

    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEdit = true;
      this.api.getTransaction(+id).subscribe(t => (this.transaction = t));
    }
  }

  save() {
    if (this.isEdit && this.transaction.id) {
      this.api.updateTransaction(this.transaction.id, this.transaction as Transaction).subscribe(() => this.router.navigate(['/transactions']));
    } else {
      this.api.createTransaction(this.transaction as Transaction).subscribe(() => this.router.navigate(['/transactions']));
    }
  }

  cancel() {
    this.router.navigate(['/transactions']);
  }
}
