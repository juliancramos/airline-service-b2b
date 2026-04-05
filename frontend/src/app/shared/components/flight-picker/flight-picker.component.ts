import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-flight-picker',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './flight-picker.component.html'
})
export class FlightPickerComponent {
  @Output() flightSelected = new EventEmitter<any>();

  // Mock basic flights strictly for UI rendering layout
  flights = [
    { id: 'f-101', flightNumber: 'VY-400', origin: 'Bogotá (BOG)', destination: 'Miami (MIA)', datetime: '2026-06-10 14:00' },
    { id: 'f-102', flightNumber: 'VY-405', origin: 'Medellín (MDE)', destination: 'Lima (LIM)', datetime: '2026-06-10 18:30' },
    { id: 'f-103', flightNumber: 'VY-510', origin: 'Cali (CLO)', destination: 'Madrid (MAD)', datetime: '2026-06-11 09:15' }
  ];

  selectFlight(flight: any): void {
    this.flightSelected.emit(flight);
  }
}
