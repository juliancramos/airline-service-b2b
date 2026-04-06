package com.airline.backend.services;

import com.airline.backend.dtos.request.FlightRequestDTO;
import com.airline.backend.dtos.response.FlightResponseDTO;
import com.airline.backend.models.enums.FlightStatus;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    FlightResponseDTO createFlight(FlightRequestDTO dto, Integer creatorId);

    FlightResponseDTO getFlightById(Integer id);

    org.springframework.data.domain.Page<FlightResponseDTO> searchFlights(String origin, String destination, String date, int page, int size);

    FlightResponseDTO updateFlightStatus(Integer id, FlightStatus status);
}
