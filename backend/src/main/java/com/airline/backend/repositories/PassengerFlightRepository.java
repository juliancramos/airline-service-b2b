package com.airline.backend.repositories;

import com.airline.backend.models.entities.PassengerFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerFlightRepository extends JpaRepository<PassengerFlight, Integer> {
}
