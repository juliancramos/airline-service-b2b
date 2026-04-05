import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Flight } from '../../shared/models/flight.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class FlightService {
  private readonly http = inject(HttpClient);
  private readonly authService = inject(AuthService);
  private readonly apiUrl = environment.apiUrl + '/flights';

  getFlights(origin?: string, destination?: string, date?: string): Observable<Flight[]> {
    let params = new HttpParams();
    if (origin) params = params.set('origin', origin);
    if (destination) params = params.set('destination', destination);
    if (date) params = params.set('date', date);
      
    return this.http.get<Flight[]>(this.apiUrl, { params });
  }

  createFlight(flightData: Flight): Observable<Flight> {
    const user = this.authService.getCurrentUser();
    let creatorId = 1;

    // Parse the string mock ID to a numeric ID since Backend expects Integer creatorId
    if (user && typeof user.id === 'string' && user.id.includes('EMP-')) {
      const parsed = parseInt(user.id.replace('EMP-', ''), 10);
      if (!isNaN(parsed)) creatorId = parsed;
    }

    const params = new HttpParams().set('creatorId', creatorId.toString());
    flightData.status = 'SCHEDULED';
    return this.http.post<Flight>(this.apiUrl, flightData, { params });
  }
}
