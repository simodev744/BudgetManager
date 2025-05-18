import {Component, destroyPlatform, inject, OnInit} from '@angular/core';
import {ApiService} from '../../../services/api.service';
import {Category} from '../../../models';

@Component({
  selector: 'app-category-form',
  imports: [],
  templateUrl: './category-form.component.html',
  standalone: true,
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent {
  private api = inject(ApiService);


  sendata() {
    this.api.createCategory({
      name: "category",
      description: "description2"
    });
  }

  private data:Category[];
  getdata(){
 this.api.getCategories().subscribe(
      data=> {
        this.data = data
      })

  }
}
