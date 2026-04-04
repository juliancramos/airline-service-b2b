package com.airline.backend.dtos.response;

import com.airline.backend.models.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightResponseDTO {

    private Integer flightId;
    private String originCity;
    private String destinationCity;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal seatPrice;
    private Integer maxCapacity;
    private FlightStatus status;
    private Integer createdById;
}
