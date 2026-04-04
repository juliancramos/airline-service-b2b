package com.airline.backend.services;

import com.airline.backend.dtos.request.EmployeeRequestDTO;
import com.airline.backend.dtos.response.EmployeeResponseDTO;

public interface InternalEmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto);

    EmployeeResponseDTO getEmployeeById(Integer id);
}
