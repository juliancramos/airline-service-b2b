package com.airline.backend.services.impl;

import com.airline.backend.dtos.request.ReservationRequestDTO;
import com.airline.backend.dtos.response.ReservationResponseDTO;
import com.airline.backend.exceptions.InsufficientCapacityException;
import com.airline.backend.exceptions.ResourceNotFoundException;
import com.airline.backend.mappers.ReservationMapper;
import com.airline.backend.models.entities.AuditEventType;
import com.airline.backend.models.entities.AuditLog;
import com.airline.backend.models.entities.Flight;
import com.airline.backend.models.entities.PassengerFlight;
import com.airline.backend.models.entities.Reservation;
import com.airline.backend.models.enums.ReservationStatus;
import com.airline.backend.repositories.AuditEventTypeRepository;
import com.airline.backend.repositories.AuditLogRepository;
import com.airline.backend.repositories.FlightRepository;
import com.airline.backend.repositories.PassengerFlightRepository;
import com.airline.backend.repositories.ReservationRepository;
import com.airline.backend.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final List<ReservationStatus> ACTIVE_STATUSES =
            List.of(ReservationStatus.pending, ReservationStatus.confirmed);

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final PassengerFlightRepository passengerFlightRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuditEventTypeRepository auditEventTypeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO request, Integer requesterId) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight", "flightId", request.getFlightId()));

        int occupiedSeats = passengerFlightRepository
                .countByReservation_Flight_FlightIdAndReservation_ReservationStatusIn(
                        flight.getFlightId(), ACTIVE_STATUSES);           
        int requestedSeats = request.getPassengers().size();

        if (occupiedSeats + requestedSeats > flight.getMaxCapacity()) {
            throw new InsufficientCapacityException(
                    flight.getFlightId(), requestedSeats, flight.getMaxCapacity() - occupiedSeats);
        }

        Reservation reservation = Reservation.builder()
                .flight(flight)
                .externalReservationId(request.getExternalReservationId())
                .reservationStatus(ReservationStatus.pending)
                .lockTimestamp(LocalDateTime.now())
                .build();

        Reservation saved = reservationRepository.save(reservation);

        List<PassengerFlight> passengers = request.getPassengers().stream()
                .map(dto -> {
                    PassengerFlight pf = reservationMapper.passengerDTOToEntity(dto);
                    pf.setReservation(saved);
                    return pf;
                })
                .toList();

        passengerFlightRepository.saveAll(passengers);

        audit("RESERVATION_CREATED",
                String.format("Reservation %d created for flight %d with %d passenger(s).",
                        saved.getReservationId(), flight.getFlightId(), requestedSeats),
                String.valueOf(requesterId));

        return buildResponse(saved, passengers);
    }

    @Override
    public ReservationResponseDTO confirmReservation(Integer reservationId, Integer requesterId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "reservationId", reservationId));

        reservation.setReservationStatus(ReservationStatus.confirmed);
        reservation.setConfirmationTimestamp(LocalDateTime.now());

        Reservation saved = reservationRepository.save(reservation);

        audit("RESERVATION_CONFIRMED",
                String.format("Reservation %d confirmed.", saved.getReservationId()),
                String.valueOf(requesterId));

        List<PassengerFlight> passengers =
                passengerFlightRepository.findByReservation_ReservationId(saved.getReservationId());

        return buildResponse(saved, passengers);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByFlight(Integer flightId) {
        if (!flightRepository.existsById(flightId)) {
            throw new ResourceNotFoundException("Flight", "flightId", flightId);
        }

        return reservationRepository.findByFlight_FlightId(flightId).stream()
                .map(r -> {
                    List<PassengerFlight> passengers =
                            passengerFlightRepository.findByReservation_ReservationId(r.getReservationId());
                    return buildResponse(r, passengers);
                })
                .toList();
    }

    // helpers

    private ReservationResponseDTO buildResponse(Reservation reservation, List<PassengerFlight> passengers) {
        ReservationResponseDTO dto = reservationMapper.toResponseDTO(reservation);
        dto.setPassengers(passengers.stream()
                .map(reservationMapper::passengerEntityToDTO)
                .toList());
        return dto;
    }

    private void audit(String eventName, String description, String requesterId) {
        AuditEventType eventType = auditEventTypeRepository.findByEventName(eventName)
                .orElseGet(() -> auditEventTypeRepository.save(
                        AuditEventType.builder().eventName(eventName).build()));

        auditLogRepository.save(AuditLog.builder()
                .eventType(eventType)
                .timestamp(LocalDateTime.now())
                .requesterId(requesterId)
                .description(description)
                .build());
    }
}
