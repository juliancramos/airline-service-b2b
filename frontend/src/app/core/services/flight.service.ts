import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Flight } from '../../shared/models/flight.model';

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  private readonly mockFlights: Flight[] = [
    {
      id: 'f-001',
      flightNumber: 'VY-1024',
      origin: 'Bogotá (BOG)',
      destination: 'Miami (MIA)',
      departureTime: '2026-05-15T08:30:00Z',
      arrivalTime: '2026-05-15T12:45:00Z',
      status: 'scheduled',
      price: 450.00,
      capacity: 150
    },
    {
      id: 'f-002',
      flightNumber: 'VY-3042',
      origin: 'Medellín (MDE)',
      destination: 'Madrid (MAD)',
      departureTime: '2026-05-15T18:00:00Z',
      arrivalTime: '2026-05-16T10:30:00Z',
      status: 'active',
      price: 850.50,
      capacity: 220
    },
    {
      id: 'f-003',
      flightNumber: 'VY-901',
      origin: 'Cali (CLO)',
      destination: 'Panamá (PTY)',
      departureTime: '2026-05-14T14:15:00Z',
      arrivalTime: '2026-05-14T15:45:00Z',
      status: 'cancelled',
      price: 210.00,
      capacity: 120
    }
  ];

  getFlights(): Observable<Flight[]> {
    return of(this.mockFlights).pipe(delay(800));
  }

  createFlight(flightData: Omit<Flight, 'id' | 'flightNumber' | 'status'>): Observable<Flight> {
    const newFlight: Flight = {
      ...flightData,
      id: `f-${Math.floor(Math.random() * 1000)}`,
      flightNumber: `VY-${Math.floor(Math.random() * 9000) + 1000}`,
      status: 'scheduled'
    };
    this.mockFlights.unshift(newFlight);
    return of(newFlight).pipe(delay(1000));
  }
}
