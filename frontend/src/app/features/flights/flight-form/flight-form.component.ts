import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FlightService } from '../../../core/services/flight.service';

@Component({
  selector: 'app-flight-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './flight-form.component.html'
})
export class FlightFormComponent {
  private readonly fb = inject(FormBuilder);
  private readonly flightService = inject(FlightService);
  private readonly router = inject(Router);

  isSubmitting = false;

  flightForm = this.fb.nonNullable.group({
    origin: ['', [Validators.required]],
    destination: ['', [Validators.required]],
    departureTime: ['', [Validators.required]],
    arrivalTime: ['', [Validators.required]],
    price: [0, [Validators.required, Validators.min(1)]],
    capacity: [100, [Validators.required, Validators.min(1)]]
  });

  onSubmit(): void {
    if (this.flightForm.invalid) {
      this.flightForm.markAllAsTouched();
      return;
    }
    
    this.isSubmitting = true;
    this.flightService.createFlight(this.flightForm.getRawValue()).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigate(['/flights']);
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
