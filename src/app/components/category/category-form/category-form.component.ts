import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {  Category } from '../../../models';
import {ApiService} from '../../../services/api.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  imports: [
    FormsModule
  ],
  standalone: true
})
export class CategoryFormComponent implements OnInit {
  category: Category = { name: '' };
  isEdit = false;

  constructor(
    private api: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEdit = true;
      this.api.getCategory(+id).subscribe(cat => (this.category = cat));
    }
  }

  save() {
    if (this.isEdit && this.category.id) {
      this.api.updateCategory(this.category.id, this.category).subscribe(() => this.router.navigate(['/categories']));
    } else {
      this.api.createCategory(this.category).subscribe(() => this.router.navigate(['/categories']));
    }
  }

  cancel() {
    this.router.navigate(['/categories']);
  }
}
