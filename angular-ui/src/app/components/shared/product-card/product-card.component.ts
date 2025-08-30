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

  getStarArray(): boolean[] {
    const stars = [];
    const rating = Math.floor(this.product().rating);
    for (let i = 0; i < 5; i++) {
      stars.push(i < rating);
    }
    return stars;
  }
}
