import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  styles: [`:host { display: contents; }`],
  templateUrl: './sidebar.html'
})
export class SidebarComponent {}
