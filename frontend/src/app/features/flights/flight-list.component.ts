import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { FlightService } from '../../core/services/flight.service';
import { Flight } from '../../shared/models/flight.model';

@Component({
  selector: 'app-flight-list',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './flight-list.component.html'
})
export class FlightListComponent implements OnInit {
  private readonly flightService = inject(FlightService);
  private readonly fb = inject(FormBuilder);

  flights: Flight[] = [];
  isLoading = true;

  filterForm = this.fb.group({
    origin: [''],
    destination: [''],
    date: ['']
  });

  ngOnInit(): void {
    this.loadFlights();
  }

  loadFlights(): void {
    this.isLoading = true;
    const { origin, destination, date } = this.filterForm.getRawValue();
    
    this.flightService.getFlights(origin || undefined, destination || undefined, date || undefined).subscribe({
      next: (data) => {
        this.flights = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.flights = [];
      }
    });
  }

  onSearch(): void {
    this.loadFlights();
  }
}
