package com.airline.backend.services.impl;

import com.airline.backend.dtos.response.DashboardMetricsDTO;
import com.airline.backend.models.enums.FlightStatus;
import com.airline.backend.models.enums.ReservationStatus;
import com.airline.backend.repositories.FlightRepository;
import com.airline.backend.repositories.ReservationRepository;
import com.airline.backend.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public DashboardMetricsDTO getMetrics() {
        long scheduledFlights = flightRepository.countByStatus(FlightStatus.scheduled);
        long confirmedReservations = reservationRepository.countByReservationStatus(ReservationStatus.confirmed);

        // Calculate flights nearing capacity (>= 90% full)
        long nearingCapacity = flightRepository.countFlightsNearingCapacity(
                FlightStatus.scheduled,
                List.of(ReservationStatus.pending, ReservationStatus.confirmed),
                0.9
        );

        return DashboardMetricsDTO.builder()
                .totalScheduledFlights(scheduledFlights)
                .totalConfirmedReservations(confirmedReservations)
                .flightsNearingCapacity(nearingCapacity)
                .build();
    }
}
