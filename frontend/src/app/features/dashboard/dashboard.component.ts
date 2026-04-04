import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { Employee } from '../../shared/models/employee.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  readonly user: Employee | null = this.authService.authState().user;

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
