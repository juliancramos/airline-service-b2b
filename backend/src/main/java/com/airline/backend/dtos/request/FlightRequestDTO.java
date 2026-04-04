package com.airline.backend.dtos.request;

import com.airline.backend.models.enums.FlightStatus;
import jakarta.validation.constraints.*;
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
public class FlightRequestDTO {

    @NotBlank
    private String originCity;

    @NotBlank
    private String destinationCity;

    @NotNull
    @FutureOrPresent
    private LocalDate departureDate;

    @NotNull
    @FutureOrPresent
    private LocalDate arrivalDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal seatPrice;

    @NotNull
    @Min(1)
    private Integer maxCapacity;

    @NotNull
    private FlightStatus status;

    @NotNull
    private Integer createdBy;
}
