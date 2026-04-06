import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { finalize } from 'rxjs/operators';
import { FlightService } from '../../core/services/flight.service';
import { Flight } from '../../shared/models/flight.model';
import { PageResponse } from '../../shared/models/pagination.model';

@Component({
  selector: 'app-flight-list',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './flight-list.component.html'
})
export class FlightListComponent implements OnInit {
  private readonly flightService = inject(FlightService);
  private readonly fb = inject(FormBuilder);
  private readonly cdr = inject(ChangeDetectorRef);

  flightPage: PageResponse<Flight> | null = null;
  isLoading = true;

  currentPage = 0;
  pageSize = 10;

  filterForm = this.fb.group({
    origin: [''],
    destination: [''],
    date: ['']
  });

  ngOnInit(): void {
    this.loadFlights(0);
  }

  loadFlights(page: number): void {
    this.isLoading = true;
    this.currentPage = page;
    
    // Explicitly parse, trim and convert empty strings to undefined for robustness
    let { origin, destination, date } = this.filterForm.getRawValue();
    const parsedOrigin = origin && origin.trim() !== '' ? origin.trim() : undefined;
    const parsedDestination = destination && destination.trim() !== '' ? destination.trim() : undefined;
    const parsedDate = date && date.trim() !== '' ? date.trim() : undefined;
    
    this.flightService.getFlights(parsedOrigin, parsedDestination, parsedDate, this.currentPage, this.pageSize)
      .pipe(finalize(() => {
        this.isLoading = false;
        // Force evaluation due to edge cases where async HTTP falls out of standard zone tree
        this.cdr.detectChanges(); 
      }))
      .subscribe({
        next: (pageData) => {
          this.flightPage = pageData;
          this.cdr.detectChanges();
        },
        error: () => {
          this.flightPage = null;
          this.cdr.detectChanges();
        }
      });
  }

  onSearch(): void {
    this.loadFlights(0);
  }

  nextPage(): void {
    if (this.flightPage && !this.flightPage.last) {
      this.loadFlights(this.currentPage + 1);
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.loadFlights(this.currentPage - 1);
    }
  }
}
