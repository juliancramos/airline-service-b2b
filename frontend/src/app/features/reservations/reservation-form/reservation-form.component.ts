import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormArray, FormGroup } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ReservationService } from '../../../core/services/reservation.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { FlightPickerComponent } from '../../../shared/components/flight-picker/flight-picker.component';

@Component({
  selector: 'app-reservation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, ModalComponent, FlightPickerComponent],
  templateUrl: './reservation-form.component.html'
})
export class ReservationFormComponent {
  private readonly fb = inject(FormBuilder);
  private readonly reservationService = inject(ReservationService);
  private readonly router = inject(Router);

  isSubmitting = false;
  isModalOpen = false;
  selectedFlight: any = null;

  reservationForm = this.fb.group({
    flightId: ['', Validators.required],
    contactEmail: ['', [Validators.required, Validators.email]],
    passengers: this.fb.array([this.createPassenger()])
  });

  get passengers(): FormArray {
    return this.reservationForm.get('passengers') as FormArray;
  }

  createPassenger(): FormGroup {
    return this.fb.nonNullable.group({
      docType: ['CC', Validators.required],
      docNumber: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    });
  }

  addPassenger(): void {
    this.passengers.push(this.createPassenger());
  }

  removePassenger(index: number): void {
    if (this.passengers.length > 1) {
      this.passengers.removeAt(index);
    }
  }

  openFlightModal(): void {
    this.isModalOpen = true;
  }

  closeFlightModal(): void {
    this.isModalOpen = false;
  }

  onFlightSelected(flight: any): void {
    this.selectedFlight = flight;
    this.reservationForm.patchValue({ flightId: flight.id });
    this.closeFlightModal();
  }

  onSubmit(): void {
    if (this.reservationForm.invalid || !this.selectedFlight) {
      this.reservationForm.markAllAsTouched();
      return;
    }
    
    this.isSubmitting = true;
    
    const formData = this.reservationForm.getRawValue();
    const payload = {
      flightNumber: this.selectedFlight.flightNumber,
      passengerCount: formData.passengers.length,
      totalPrice: formData.passengers.length * 450, // mock standard pricing per passenger
      contactEmail: formData.contactEmail,
      passengers: formData.passengers
    };

    this.reservationService.createReservation(payload).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigate(['/reservations']);
      },
      error: () => {
        this.isSubmitting = false;
      }
    });
  }
}
