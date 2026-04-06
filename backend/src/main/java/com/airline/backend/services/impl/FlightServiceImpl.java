package com.airline.backend.services.impl;

import com.airline.backend.dtos.request.FlightRequestDTO;
import com.airline.backend.dtos.response.FlightResponseDTO;
import com.airline.backend.exceptions.ResourceNotFoundException;
import com.airline.backend.mappers.FlightMapper;
import com.airline.backend.models.entities.Flight;
import com.airline.backend.models.entities.InternalEmployee;
import com.airline.backend.models.enums.FlightStatus;
import com.airline.backend.repositories.FlightRepository;
import com.airline.backend.repositories.InternalEmployeeRepository;
import com.airline.backend.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final InternalEmployeeRepository employeeRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponseDTO createFlight(FlightRequestDTO dto, Integer creatorId) {
        InternalEmployee creator = employeeRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", creatorId));

        Flight flight = flightMapper.toEntity(dto);
        flight.setCreatedBy(creator);

        return flightMapper.toResponseDTO(flightRepository.save(flight));
    }

    @Override
    @Transactional(readOnly = true)
    public FlightResponseDTO getFlightById(Integer id) {
        return flightRepository.findById(id)
                .map(flightMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "flightId", id));
    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<FlightResponseDTO> searchFlights(String origin, String destination, String date, int page, int size) {
        String mappedOrigin = (origin != null && !origin.trim().isEmpty()) ? origin : null;
        String mappedDest = (destination != null && !destination.trim().isEmpty()) ? destination : null;
        java.time.LocalDate mappedDate = (date != null && !date.trim().isEmpty()) ? java.time.LocalDate.parse(date.trim()) : null;

        org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(page, size);

        return flightRepository
                .searchOptionalFlights(mappedOrigin, mappedDest, mappedDate, pageRequest)
                .map(flightMapper::toResponseDTO);
    }

    @Override
    public FlightResponseDTO updateFlightStatus(Integer id, FlightStatus status) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "flightId", id));

        flight.setStatus(status);

        return flightMapper.toResponseDTO(flightRepository.save(flight));
    }
}
