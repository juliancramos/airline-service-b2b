import { Component, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormArray, FormGroup } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs/operators';
import { ReservationService } from '../../../core/services/reservation.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { FlightPickerComponent } from '../../../shared/components/flight-picker/flight-picker.component';
import { Flight } from '../../../shared/models/flight.model';

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
  private readonly cdr = inject(ChangeDetectorRef);

  isSubmitting = false;
  isModalOpen = false;
  selectedFlight: Flight | null = null;

  get totalReservationPrice(): number {
    if (!this.selectedFlight || !this.selectedFlight.seatPrice) return 0;
    return this.passengers.length * this.selectedFlight.seatPrice;
  }

  reservationForm = this.fb.group({
    flightId: [null as number | null, Validators.required],
    contactEmail: ['', [Validators.required, Validators.email]],
    passengers: this.fb.array([this.createPassenger()])
  });

  get passengers(): FormArray {
    return this.reservationForm.get('passengers') as FormArray;
  }

  createPassenger(): FormGroup {
    return this.fb.nonNullable.group({
      documentType: ['CC', Validators.required],
      documentNumber: ['', Validators.required],
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

  onFlightSelected(flight: Flight): void {
    this.selectedFlight = flight;
    this.reservationForm.patchValue({ flightId: flight.flightId });
    this.closeFlightModal();
  }

  onSubmit(): void {
    if (this.reservationForm.invalid || !this.selectedFlight) {
      this.reservationForm.markAllAsTouched();
      return;
    }
    
    this.isSubmitting = true;
    
    const formData = this.reservationForm.getRawValue();
    // Only map the parameters accepted by Java backend ReservationRequestDTO
    const payload = {
      flightId: formData.flightId,
      passengers: formData.passengers
    };

    this.reservationService.createReservation(payload).pipe(
      finalize(() => {
        this.isSubmitting = false;
        this.cdr.detectChanges();
      })
    ).subscribe({
      next: () => {
        this.router.navigate(['/reservations']);
      },
      error: () => {
        // UI naturally unlocked via finalize
      }
    });
  }
}
