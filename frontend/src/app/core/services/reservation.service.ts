import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Reservation } from '../../shared/models/reservation.model';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private readonly mockReservations: Reservation[] = [
    {
      id: 'RES-001',
      flightNumber: 'VY-1024',
      passengerCount: 2,
      totalPrice: 900.00,
      status: 'confirmed',
      createdAt: '2026-05-10T09:15:00Z',
      contactEmail: 'cliente@empresa.com'
    },
    {
      id: 'RES-002',
      flightNumber: 'VY-3042',
      passengerCount: 1,
      totalPrice: 850.50,
      status: 'pending',
      createdAt: '2026-05-12T14:20:00Z',
      contactEmail: 'ejecutivo@corp.com'
    },
    {
      id: 'RES-003',
      flightNumber: 'VY-1024',
      passengerCount: 4,
      totalPrice: 1800.00,
      status: 'confirmed',
      createdAt: '2026-05-13T08:05:00Z',
      contactEmail: 'equipo@agencia.com'
    },
    {
      id: 'RES-004',
      flightNumber: 'VY-555',
      passengerCount: 1,
      totalPrice: 520.00,
      status: 'pending',
      createdAt: '2026-05-14T11:45:00Z',
      contactEmail: 'viajero@personal.com'
    },
    {
      id: 'RES-005',
      flightNumber: 'VY-202',
      passengerCount: 3,
      totalPrice: 930.00,
      status: 'confirmed',
      createdAt: '2026-05-15T16:30:00Z',
      contactEmail: 'manager@startup.io'
    }
  ];

  getReservations(): Observable<Reservation[]> {
    return of(this.mockReservations).pipe(delay(800));
  }
}
