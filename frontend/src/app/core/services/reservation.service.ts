import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Reservation } from '../../shared/models/reservation.model';
import { PageResponse } from '../../shared/models/pagination.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = environment.apiUrl + '/reservations';

  searchReservations(
    flightId?: string,
    origin?: string,
    destination?: string,
    date?: string,
    passengerDocument?: string,
    page: number = 0,
    size: number = 10
  ): Observable<PageResponse<Reservation>> {
    let params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString());
        
    if (flightId) params = params.set('flightId', flightId);
    if (origin) params = params.set('origin', origin);
    if (destination) params = params.set('destination', destination);
    if (date) params = params.set('date', date);
    if (passengerDocument) params = params.set('passengerDocument', passengerDocument);
      
    return this.http.get<PageResponse<Reservation>>(`${this.apiUrl}/search`, { params });
  }

  createReservation(reservationData: any): Observable<Reservation> {
    const user = this.authService.getCurrentUser();
    let requesterId = 1;

    if (user && typeof user.id === 'string' && user.id.includes('EMP-')) {
      const parsed = parseInt(user.id.replace('EMP-', ''), 10);
      if (!isNaN(parsed)) requesterId = parsed;
    }

    const params = new HttpParams().set('requesterId', requesterId.toString());
    return this.http.post<Reservation>(this.apiUrl, reservationData, { params });
  }
}
