import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FlightService } from '../../../core/services/flight.service';
import { Flight } from '../../../shared/models/flight.model';

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
    originCity: ['', [Validators.required]],
    destinationCity: ['', [Validators.required]],
    departureDate: ['', [Validators.required]],
    startTime: ['', [Validators.required]],
    arrivalDate: ['', [Validators.required]],
    endTime: ['', [Validators.required]],
    seatPrice: [0, [Validators.required, Validators.min(0.01)]],
    maxCapacity: [100, [Validators.required, Validators.min(1)]]
  });

  onSubmit(): void {
    if (this.flightForm.invalid) {
      this.flightForm.markAllAsTouched();
      return;
    }
    
    this.isSubmitting = true;
    const rawData = this.flightForm.getRawValue();
    
    let parsedStartTime = rawData.startTime;
    let parsedEndTime = rawData.endTime;
    
    // Add seconds to match LocalTime (HH:mm:ss) Java type seamlessly
    if (parsedStartTime.length === 5) parsedStartTime += ':00';
    if (parsedEndTime.length === 5) parsedEndTime += ':00';

    const newFlight: Flight = {
      originCity: rawData.originCity,
      destinationCity: rawData.destinationCity,
      departureDate: rawData.departureDate,
      startTime: parsedStartTime,
      arrivalDate: rawData.arrivalDate,
      endTime: parsedEndTime,
      seatPrice: rawData.seatPrice,
      maxCapacity: rawData.maxCapacity
    };

    this.flightService.createFlight(newFlight).subscribe({
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
