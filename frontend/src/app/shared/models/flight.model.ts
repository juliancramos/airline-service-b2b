export type FlightStatus = 'scheduled' | 'active' | 'cancelled';

export interface Flight {
  id: string;
  flightNumber: string;
  origin: string;
  destination: string;
  departureTime: string;
  arrivalTime: string;
  status: FlightStatus;
  price: number;
  capacity: number;
}
