import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlightService } from '../../core/services/flight.service';
import { Flight } from '../../shared/models/flight.model';

@Component({
  selector: 'app-flight-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './flight-list.component.html'
})
export class FlightListComponent implements OnInit {
  private readonly flightService = inject(FlightService);

  flights: Flight[] = [];
  isLoading = true;

  ngOnInit(): void {
    this.loadFlights();
  }

  loadFlights(): void {
    this.isLoading = true;
    this.flightService.getFlights().subscribe({
      next: (data) => {
        this.flights = data;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }
}
