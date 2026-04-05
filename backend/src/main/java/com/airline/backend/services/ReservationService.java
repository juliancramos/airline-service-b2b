package com.airline.backend.services;

import com.airline.backend.dtos.request.ReservationRequestDTO;
import com.airline.backend.dtos.response.ReservationResponseDTO;

import java.util.List;

public interface ReservationService {

    ReservationResponseDTO createReservation(ReservationRequestDTO request, Integer requesterId);

    ReservationResponseDTO confirmReservation(Integer reservationId, Integer requesterId);

    List<ReservationResponseDTO> getReservationsByFlight(Integer flightId);

    List<ReservationResponseDTO> searchReservations(
            Integer flightId, String origin, String destination, java.time.LocalDate date, String passengerDocument);
}
