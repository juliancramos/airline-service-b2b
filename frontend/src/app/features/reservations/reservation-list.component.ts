import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { finalize } from 'rxjs/operators';
import { ReservationService } from '../../core/services/reservation.service';
import { Reservation } from '../../shared/models/reservation.model';
import { PageResponse } from '../../shared/models/pagination.model';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './reservation-list.component.html'
})
export class ReservationListComponent implements OnInit {
  private readonly reservationService = inject(ReservationService);
  private readonly fb = inject(FormBuilder);
  private readonly cdr = inject(ChangeDetectorRef);

  reservationPage: PageResponse<Reservation> | null = null;
  isLoading = true;

  currentPage = 0;
  pageSize = 10;

  filterForm = this.fb.group({
    flightId: [''],
    origin: [''],
    destination: [''],
    date: [''],
    passengerDocument: ['']
  });

  ngOnInit(): void {
    this.loadReservations(0);
  }

  loadReservations(page: number): void {
    this.isLoading = true;
    this.currentPage = page;
    
    let raw = this.filterForm.getRawValue();
    const flightId = raw.flightId && raw.flightId.trim() !== '' ? raw.flightId.trim() : undefined;
    const origin = raw.origin && raw.origin.trim() !== '' ? raw.origin.trim() : undefined;
    const destination = raw.destination && raw.destination.trim() !== '' ? raw.destination.trim() : undefined;
    const date = raw.date && raw.date.trim() !== '' ? raw.date.trim() : undefined;
    const passengerDocument = raw.passengerDocument && raw.passengerDocument.trim() !== '' ? raw.passengerDocument.trim() : undefined;

    this.reservationService.searchReservations(flightId, origin, destination, date, passengerDocument, this.currentPage, this.pageSize)
      .pipe(finalize(() => {
        this.isLoading = false;
        this.cdr.detectChanges();
      }))
      .subscribe({
        next: (pageData) => {
          this.reservationPage = pageData;
          this.cdr.detectChanges();
        },
        error: () => {
          this.reservationPage = null;
          this.cdr.detectChanges();
        }
      });
  }

  onSearch(): void {
    this.loadReservations(0);
  }

  nextPage(): void {
    if (this.reservationPage && !this.reservationPage.last) {
      this.loadReservations(this.currentPage + 1);
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.loadReservations(this.currentPage - 1);
    }
  }
}
