package com.airline.backend.dtos.response;

import com.airline.backend.dtos.request.PassengerDTO;
import com.airline.backend.models.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDTO {

    private Integer reservationId;
    private Integer flightId;
    private String externalReservationId;
    private ReservationStatus reservationStatus;
    private LocalDateTime lockTimestamp;
    private LocalDateTime confirmationTimestamp;
    private List<PassengerDTO> passengers;
}
