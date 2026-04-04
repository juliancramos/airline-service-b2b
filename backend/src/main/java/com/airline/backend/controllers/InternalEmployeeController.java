package com.airline.backend.controllers;

import com.airline.backend.dtos.request.EmployeeRequestDTO;
import com.airline.backend.dtos.response.EmployeeResponseDTO;
import com.airline.backend.services.InternalEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class InternalEmployeeController {

    private final InternalEmployeeService internalEmployeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(internalEmployeeService.createEmployee(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.ok(internalEmployeeService.getEmployeeById(id));
    }
}
