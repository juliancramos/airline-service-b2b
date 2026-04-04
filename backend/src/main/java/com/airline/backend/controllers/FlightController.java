package com.airline.backend.controllers;

import com.airline.backend.dtos.request.FlightRequestDTO;
import com.airline.backend.dtos.response.FlightResponseDTO;
import com.airline.backend.services.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightResponseDTO>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(flightService.searchFlights(origin, destination, date));
    }

    @PostMapping
    public ResponseEntity<FlightResponseDTO> createFlight(
            @Valid @RequestBody FlightRequestDTO request,
            @RequestParam Integer creatorId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.createFlight(request, creatorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable Integer id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }
}
