import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../../models/product.model';
import { ProductCardComponent } from '../../shared/product-card/product-card.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-top-products',
  standalone: true,
  imports: [CommonModule, ProductCardComponent, MatExpansionModule, MatIconModule],
  templateUrl: './top-products.component.html',
  styleUrl: './top-products.component.scss',
})
export class TopProductsComponent implements OnInit {
  products: Product[] = [];
  panelOpen = true;

  ngOnInit() {
    this.loadMockProducts();
  }

  togglePanel() {
    this.panelOpen = !this.panelOpen;
  }

  private loadMockProducts() {
    this.products = [
      {
        name: 'Wireless Bluetooth Headphones',
        description:
          'Premium quality wireless headphones with noise cancellation and 30-hour battery life.',
        sellingPrice: 89.99,
        currentPrice: 129.99,
        imageUrl:
          'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=300&fit=crop',
        categoryName: 'Electronics',
        rating: 4.5,
        reviewCount: 1247,
        inStock: true,
        tags: ['wireless', 'bluetooth', 'noise-cancelling'],
      },
      {
        name: 'Smart Fitness Watch',
        description:
          'Advanced fitness tracking with heart rate monitor, GPS, and 7-day battery life.',
        sellingPrice: 199.99,
        currentPrice: 249.99,
        imageUrl:
          'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&h=300&fit=crop',
        categoryName: 'Electronics',
        rating: 2.5,
        reviewCount: 892,
        inStock: true,
        tags: ['fitness', 'smartwatch', 'health'],
      },
      {
        name: 'Organic Cotton T-Shirt',
        description:
          'Comfortable and sustainable organic cotton t-shirt in various colors and sizes.',
        sellingPrice: 24.99,
        currentPrice: 34.99,
        imageUrl:
          'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&h=300&fit=crop',
        categoryName: 'Fashion',
        rating: 4.3,
        reviewCount: 456,
        inStock: true,
        tags: ['organic', 'cotton', 'sustainable'],
      },
      {
        name: 'Professional Coffee Maker',
        description:
          'Programmable coffee maker with built-in grinder and thermal carafe for perfect coffee.',
        sellingPrice: 149.99,
        currentPrice: 199.99,
        imageUrl:
          'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&h=300&fit=crop',
        categoryName: 'Home',
        rating: 4.6,
        reviewCount: 723,
        inStock: true,
        tags: ['coffee', 'kitchen', 'appliance'],
      },
      {
        name: 'Yoga Mat Premium',
        description: 'Non-slip premium yoga mat with alignment lines and carrying strap included.',
        sellingPrice: 39.99,
        currentPrice: 59.99,
        imageUrl: 'https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400&h=300&fit=crop',
        categoryName: 'Sports',
        rating: 4.4,
        reviewCount: 334,
        inStock: false,
        tags: ['yoga', 'fitness', 'exercise'],
      },
      {
        name: 'Bestselling Novel Collection',
        description:
          'Collection of 5 bestselling novels from award-winning authors in hardcover edition.',
        sellingPrice: 79.99,
        currentPrice: 99.99,
        imageUrl:
          'https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400&h=300&fit=crop',
        categoryName: 'Books',
        rating: 4.7,
        reviewCount: 567,
        inStock: true,
        tags: ['books', 'novels', 'collection'],
      },
      {
        name: 'Wireless Gaming Mouse',
        description:
          'High-precision wireless gaming mouse with RGB lighting and programmable buttons.',
        sellingPrice: 69.99,
        currentPrice: 89.99,
        imageUrl:
          'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400&h=300&fit=crop',
        categoryName: 'Electronics',
        rating: 4.5,
        reviewCount: 891,
        inStock: true,
        tags: ['gaming', 'mouse', 'wireless'],
      },
      {
        name: 'Ceramic Plant Pot Set',
        description: 'Set of 3 modern ceramic plant pots with drainage holes and saucers included.',
        sellingPrice: 34.99,
        currentPrice: 49.99,
        imageUrl:
          'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=400&h=300&fit=crop',
        categoryName: 'Home',
        rating: 4.2,
        reviewCount: 245,
        inStock: true,
        tags: ['plants', 'ceramic', 'home-decor'],
      },
    ];
  }
}
