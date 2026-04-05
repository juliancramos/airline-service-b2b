import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './header/header';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent],
  styles: [`:host { display: contents; }`],
  templateUrl: './layout.html'
})
export class LayoutComponent {}
