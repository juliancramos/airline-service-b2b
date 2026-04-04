package com.airline.backend.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassengerDTO {

    @NotBlank
    private String documentType;

    @NotBlank
    private String documentNumber;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
