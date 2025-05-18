import { Component, OnInit } from '@angular/core';
import { Category } from '../../../models';
import {Router, RouterLink} from '@angular/router';
import {ApiService} from '../../../services/api.service';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  imports: [
    RouterLink,
    NgForOf
  ],
  standalone: true
})
export class CategoryListComponent implements OnInit {
  categories: Category[] = [];

  constructor(private api: ApiService, private router: Router) {}

  ngOnInit() {
    this.loadCategories();
  }

  loadCategories() {
    this.api.getCategories().subscribe(data => (this.categories = data));
  }

  deleteCategory(id: number) {
    if (confirm('Delete this category?')) {
      this.api.deleteCategory(id).subscribe(() => this.loadCategories());
    }
  }
}
