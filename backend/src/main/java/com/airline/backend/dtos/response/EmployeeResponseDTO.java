package com.airline.backend.dtos.response;

import com.airline.backend.models.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDTO {

    private Integer employeeId;
    private String documentType;
    private String documentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String roleName;
    private EmployeeStatus status;
}
