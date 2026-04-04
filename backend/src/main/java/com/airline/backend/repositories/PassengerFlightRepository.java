package com.airline.backend.repositories;

import com.airline.backend.models.entities.PassengerFlight;
import com.airline.backend.models.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerFlightRepository extends JpaRepository<PassengerFlight, Integer> {

    int countByReservation_Flight_FlightIdAndReservation_ReservationStatusIn(
            Integer flightId, List<ReservationStatus> statuses);

    List<PassengerFlight> findByReservation_ReservationId(Integer reservationId);
}
