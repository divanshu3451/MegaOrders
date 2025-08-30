import { Injectable, signal, effect } from '@angular/core';

type ThemeMode = 'light' | 'dark' | 'system';
const STORAGE_KEY = 'theme.mode';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  readonly mode = signal<ThemeMode>(this.read());
  private media = window.matchMedia('(prefers-color-scheme: dark)');

  constructor() {
    this.apply();
    this.media.addEventListener('change', () => {
      if (this.mode() === 'system') this.apply();
    });
    effect(() => {
      this.store(this.mode());
      this.apply();
    });
  }

  setLight() {
    this.mode.set('light');
  }
  setDark() {
    this.mode.set('dark');
  }
  setSystem() {
    this.mode.set('system');
  }
  toggle() {
    this.mode.set(this.effective() === 'dark' ? 'light' : 'dark');
  }

  private apply() {
    const html = document.documentElement;
    html.classList.remove('light-theme', 'dark-theme');
    html.classList.add(
      this.effective() === 'dark' ? 'dark-theme' : 'light-theme'
    );
  }
  private effective(): 'light' | 'dark' {
    const m = this.mode();
    return m === 'system' ? (this.media.matches ? 'dark' : 'light') : m;
  }
  private read(): ThemeMode {
    return (localStorage.getItem(STORAGE_KEY) as ThemeMode) ?? 'system';
  }
  private store(m: ThemeMode) {
    localStorage.setItem(STORAGE_KEY, m);
  }
}
