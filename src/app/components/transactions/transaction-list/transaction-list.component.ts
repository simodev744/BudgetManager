import { Component, OnInit } from '@angular/core';
import {  Transaction } from '../../../models';
import {Router, RouterLink} from '@angular/router';
import {ApiService} from '../../../services/api.service';
import {CurrencyPipe, DatePipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  imports: [
    RouterLink,
    CurrencyPipe,
    NgForOf,
    DatePipe
  ],
  standalone: true
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[] = [];

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit() {
    this.loadTransactions();
  }

  loadTransactions() {
    this.api.getTransactions().subscribe(data => (this.transactions = data));
  }

  deleteTransaction(id: number) {
    if (confirm('Delete this transaction?')) {
      this.api.deleteTransaction(id).subscribe(() => this.loadTransactions());
    }
  }
}
