import { Injectable } from '@angular/core';
import { Observable, of, delay } from 'rxjs';
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
      price: 450.00
    },
    {
      id: 'f-002',
      flightNumber: 'VY-3042',
      origin: 'Medellín (MDE)',
      destination: 'Madrid (MAD)',
      departureTime: '2026-05-15T18:00:00Z',
      arrivalTime: '2026-05-16T10:30:00Z',
      status: 'active',
      price: 850.50
    },
    {
      id: 'f-003',
      flightNumber: 'VY-901',
      origin: 'Cali (CLO)',
      destination: 'Panamá (PTY)',
      departureTime: '2026-05-14T14:15:00Z',
      arrivalTime: '2026-05-14T15:45:00Z',
      status: 'cancelled',
      price: 210.00
    },
    {
      id: 'f-004',
      flightNumber: 'VY-555',
      origin: 'Cartagena (CTG)',
      destination: 'Nueva York (JFK)',
      departureTime: '2026-05-16T09:00:00Z',
      arrivalTime: '2026-05-16T14:20:00Z',
      status: 'scheduled',
      price: 520.00
    },
    {
      id: 'f-005',
      flightNumber: 'VY-202',
      origin: 'Bogotá (BOG)',
      destination: 'Lima (LIM)',
      departureTime: '2026-05-17T22:30:00Z',
      arrivalTime: '2026-05-18T01:45:00Z',
      status: 'scheduled',
      price: 310.00
    }
  ];

  getFlights(): Observable<Flight[]> {
    return of(this.mockFlights).pipe(delay(800));
  }
}
