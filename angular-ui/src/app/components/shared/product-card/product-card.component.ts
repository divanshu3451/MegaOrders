import { Component, input, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../../models/product.model';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatChipsModule, MatIconModule, MatButtonModule],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss',
})
export class ProductCardComponent {
  product = input.required<Product>();

  getDiscountPercentage(): number {
    if (this.product()?.currentPrice && this.product().sellingPrice < this.product().currentPrice) {
      return Math.round(
        ((this.product().currentPrice - this.product().sellingPrice) /
          this.product().currentPrice) *
          100,
      );
    }
    return 0;
  }

  getStarArray() {
    const stars = [];
    const rating = this.product().rating;

    for (let i = 1; i <= 5; i++) {
      if (i <= Math.floor(rating)) {
        stars.push({ icon: 'star', class: 'star-filled' });
      } else if (i === Math.ceil(rating) && rating % 1 !== 0) {
        stars.push({ icon: 'star_half', class: 'star-half' });
      } else {
        stars.push({ icon: 'star_outline', class: 'star-empty' });
      }
    }

    return stars;
  }

  addToCart() {
    if (this.product().inStock) {
      console.log('Added to cart:', this.product.name);
      // Implement your add to cart logic here
    }
  }
}
