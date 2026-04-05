import { Injectable, signal } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { delay, tap } from 'rxjs/operators';
import { Employee } from '../../shared/models/employee.model';

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface AuthState {
  user: Employee | null;
  isAuthenticated: boolean;
}

const MOCK_EMPLOYEES: Employee[] = [
  {
    id: 'EMP-001',
    email: 'julian.ramos@skyline.aero',
    name: 'Julian Ramos',
    role: 'ADMIN',
    department: 'Operations',
    avatarInitials: 'JR',
  }
];

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _authState = signal<AuthState>({
    user: null,
    isAuthenticated: false,
  });

  readonly authState = this._authState.asReadonly();

  login(credentials: LoginCredentials): Observable<Employee> {
    const { email, password } = credentials;

    if (!email || !password) {
      return throwError(() => new Error('Email and password are required.'));
    }

    const matchedEmployee =
      MOCK_EMPLOYEES.find((emp) => emp.email === email) ?? MOCK_EMPLOYEES[0];

    return of(matchedEmployee).pipe(
      delay(1500),
      tap((employee) => {
        this._authState.set({ user: employee, isAuthenticated: true });
      }),
    );
  }

  logout(): void {
    this._authState.set({ user: null, isAuthenticated: false });
  }

  getCurrentUser(): Employee | null {
    return this._authState().user;
  }
}
