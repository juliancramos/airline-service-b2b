export type ReservationStatus = 'confirmed' | 'pending';

export interface PassengerDTO {
    passengerId?: number;
    firstName: string;
    lastName: string;
    documentType: string;
    documentNumber: string;
}

export interface Reservation {
  reservationId: number;
  flightId: number;
  externalReservationId: string;
  reservationStatus: ReservationStatus;
  lockTimestamp: string;
  confirmationTimestamp: string;
  passengers: PassengerDTO[];
}
