package com.airline.backend.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDTO {

    @NotNull
    private Integer flightId;

    private String externalReservationId;

    @NotEmpty
    @Valid
    private List<PassengerDTO> passengers;
}
