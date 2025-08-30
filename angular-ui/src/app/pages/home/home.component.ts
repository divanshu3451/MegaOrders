import { Component } from '@angular/core';
import { HeaderComponent } from "../../components/public/header/header.component";
import { HeroSectionComponent } from "../../components/public/hero-section/hero-section.component";
import { FeaturedCategoriesComponent } from "../../components/public/featured-categories/featured-categories.component";
import { TopProductsComponent } from "../../components/public/top-products/top-products.component";

@Component({
  selector: 'app-home',
  imports: [HeaderComponent, HeroSectionComponent, FeaturedCategoriesComponent, TopProductsComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
