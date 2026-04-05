package com.airline.backend.repositories;

import com.airline.backend.models.entities.Flight;
import com.airline.backend.models.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findByOriginCityAndDestinationCityAndDepartureDate(
            String originCity,
            String destinationCity,
            LocalDate departureDate
    );

    List<Flight> findByStatus(FlightStatus status);

    long countByStatus(FlightStatus status);

    @org.springframework.data.jpa.repository.Query("""
        SELECT COUNT(f) FROM Flight f
        WHERE f.status = :flightStatus
        AND (
            SELECT COUNT(pf) FROM PassengerFlight pf
            WHERE pf.reservation.flight = f
            AND pf.reservation.reservationStatus IN :reservationStatuses
        ) >= (f.maxCapacity * :threshold)
    """)
    long countFlightsNearingCapacity(
            @org.springframework.data.repository.query.Param("flightStatus") FlightStatus flightStatus,
            @org.springframework.data.repository.query.Param("reservationStatuses") List<com.airline.backend.models.enums.ReservationStatus> reservationStatuses,
            @org.springframework.data.repository.query.Param("threshold") double threshold
    );
}
