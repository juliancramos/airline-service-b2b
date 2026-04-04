package com.airline.backend.repositories;

import com.airline.backend.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByFlight_FlightId(Integer flightId);

    Optional<Reservation> findByExternalReservationId(String externalReservationId);
}
