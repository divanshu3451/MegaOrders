import { Component, computed, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { ThemeService } from '../../../services/theme.service';

@Component({
  selector: 'app-theme-toggle',
  imports: [MatIconModule],
  templateUrl: './theme-toggle.component.html',
  styleUrl: './theme-toggle.component.scss',
})
export class ThemeToggleComponent {
  private theme = inject(ThemeService);
  private systemPrefersDark = window.matchMedia('(prefers-color-scheme: dark)');

  effective = computed<'light' | 'dark'>(() => {
    const mode = this.theme.mode();
    if (mode === 'system') {
      return this.systemPrefersDark.matches ? 'dark' : 'light';
    }
    return mode;
  });

  toggleDarkLight(): void {
    this.theme.toggle();
  }

  setSystem(): void {
    this.theme.setSystem();
  }
}
