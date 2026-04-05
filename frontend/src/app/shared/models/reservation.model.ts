export type ReservationStatus = 'confirmed' | 'pending';

export interface Reservation {
  id: string;
  flightNumber: string;
  passengerCount: number;
  totalPrice: number;
  status: ReservationStatus;
  createdAt: string; 
  contactEmail: string;
  passengerDocument?: string;
}
