package com.airline.backend.controllers;

import com.airline.backend.dtos.request.ReservationRequestDTO;
import com.airline.backend.dtos.response.ReservationResponseDTO;
import com.airline.backend.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @Valid @RequestBody ReservationRequestDTO request,
            @RequestParam Integer requesterId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(request, requesterId));
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponseDTO> confirmReservation(
            @PathVariable Integer id,
            @RequestParam Integer requesterId) {

        return ResponseEntity.ok(reservationService.confirmReservation(id, requesterId));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByFlight(
            @PathVariable Integer flightId) {

        return ResponseEntity.ok(reservationService.getReservationsByFlight(flightId));
    }

    @GetMapping("/search")
    public ResponseEntity<org.springframework.data.domain.Page<ReservationResponseDTO>> searchReservations(
            @RequestParam(required = false) Integer flightId,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate date,
            @RequestParam(required = false) String passengerDocument,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(reservationService.searchReservations(
                flightId, origin, destination, date, passengerDocument, page, size));
    }
}
