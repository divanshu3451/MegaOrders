import { Category } from './../../../models/category.model';
import { Component, ElementRef, signal, ViewChild, computed } from '@angular/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { categories } from './categories';

@Component({
  selector: 'app-featured-categories',
  imports: [MatGridListModule, MatCardModule, MatChipsModule, MatTooltipModule, MatIconModule],
  templateUrl: './featured-categories.component.html',
  styleUrl: './featured-categories.component.scss',
})
export class FeaturedCategoriesComponent {
  categories = signal<Category[]>(categories);

  @ViewChild('scroller', { static: true })
  scroller!: ElementRef<HTMLDivElement>;

  private scrollLeft = signal(0);

  onScroll() {
    const element = this.scroller?.nativeElement;
    this.scrollLeft.set(element.scrollLeft);
  }

  isAtStart = computed(() => {
    const threshold = 10;
    return this.scrollLeft() <= threshold;
  });

  isAtEnd = computed(() => {
    if (!this.scroller) return true;
    const element = this.scroller.nativeElement;
    const threshold = 10;
    return this.scrollLeft() >= element.scrollWidth - element.clientWidth - threshold;
  });

  scrollBy(direction: number) {
    const element = this.scroller?.nativeElement;
    const scrollAmount = Math.max(240, element.clientWidth * 0.6) * direction;
    element.scrollBy({ left: scrollAmount, behavior: 'smooth' });
  }

  onSelect(c: Category): void {
    // navigate/apply filter as needed
  }

  ngAfterViewInit() {
    this.onScroll(); // Initialize scroll position signals after view init
  }
}
