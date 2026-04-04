package com.airline.backend.services.impl;

import com.airline.backend.dtos.request.EmployeeRequestDTO;
import com.airline.backend.dtos.response.EmployeeResponseDTO;
import com.airline.backend.exceptions.ResourceNotFoundException;
import com.airline.backend.mappers.EmployeeMapper;
import com.airline.backend.models.entities.InternalEmployee;
import com.airline.backend.models.entities.Role;
import com.airline.backend.repositories.InternalEmployeeRepository;
import com.airline.backend.repositories.RoleRepository;
import com.airline.backend.services.InternalEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InternalEmployeeServiceImpl implements InternalEmployeeService {

    private final InternalEmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "roleId", dto.getRoleId()));

        InternalEmployee employee = employeeMapper.toEntity(dto);

        // FALTA ENCRIPTAR LA CONTRASEÑA
        employee.setRole(role);

        return employeeMapper.toResponseDTO(employeeRepository.save(employee));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", id));
    }
}
