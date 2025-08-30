export interface Product {
  name: string;
  description: string;
  currentPrice: number;
  sellingPrice: number;
  imageUrl: string;
  categoryName: string;
  rating: number;
  reviewCount: number;
  inStock: boolean;
  tags?: string[];
}
