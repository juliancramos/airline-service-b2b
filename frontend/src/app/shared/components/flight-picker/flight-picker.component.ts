import { Component, Output, EventEmitter, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlightService } from '../../../core/services/flight.service';
import { Flight } from '../../models/flight.model';

@Component({
  selector: 'app-flight-picker',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './flight-picker.component.html'
})
export class FlightPickerComponent implements OnInit {
  @Output() flightSelected = new EventEmitter<Flight>();
  private readonly flightService = inject(FlightService);

  flights: Flight[] = [];
  isLoading = true;

  ngOnInit(): void {
    // Dynamically fetch first page of completely available flights with no filters required
    this.flightService.getFlights().subscribe({
      next: (pageData) => {
        this.flights = pageData.content;
        this.isLoading = false;
      },
      error: () => this.isLoading = false
    });
  }

  selectFlight(flight: Flight): void {
    this.flightSelected.emit(flight);
  }
}
