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
}
