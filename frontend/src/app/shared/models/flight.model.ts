export type FlightStatus = 'SCHEDULED' | 'ACTIVE' | 'CANCELLED';

export interface Flight {
  flightId?: number;
  originCity: string;
  destinationCity: string;
  departureDate: string;
  arrivalDate: string;
  startTime: string;
  endTime: string;
  seatPrice: number;
  maxCapacity: number;
  status?: FlightStatus;
  createdById?: number;
}
