package com.airline.backend.repositories;

import com.airline.backend.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByFlight_FlightId(Integer flightId);

    Optional<Reservation> findByExternalReservationId(String externalReservationId);

    long countByReservationStatus(com.airline.backend.models.enums.ReservationStatus status);

    @Query("""
        SELECT r FROM Reservation r
        WHERE (:flightId IS NULL OR r.flight.flightId = :flightId)
        AND (:origin IS NULL OR LOWER(r.flight.originCity) LIKE LOWER(CONCAT('%', CAST(:origin AS text), '%')))
        AND (:destination IS NULL OR LOWER(r.flight.destinationCity) LIKE LOWER(CONCAT('%', CAST(:destination AS text), '%')))
        AND (CAST(:date AS date) IS NULL OR r.flight.departureDate = :date)
        AND (:passengerDocument IS NULL OR EXISTS (
            SELECT 1 FROM PassengerFlight pf 
            WHERE pf.reservation = r AND pf.documentNumber = :passengerDocument
        ))
        ORDER BY r.lockTimestamp DESC
    """)
    org.springframework.data.domain.Page<Reservation> findProjectedByFilters(
            @Param("flightId") Integer flightId,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("date") LocalDate date,
            @Param("passengerDocument") String passengerDocument,
            org.springframework.data.domain.Pageable pageable
    );
}
